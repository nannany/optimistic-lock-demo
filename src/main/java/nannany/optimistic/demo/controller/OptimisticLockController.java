package nannany.optimistic.demo.controller;

import nannany.optimistic.demo.EntityToResultMapper;
import nannany.optimistic.demo.RequestToEntityMapper;
import nannany.optimistic.demo.infra.DemoDataEntity;
import nannany.optimistic.demo.infra.DemoDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static nannany.optimistic.demo.util.DemoUtils.*;

@RestController
@RequestMapping("optimistic")
public class OptimisticLockController {

    private DemoDataRepository demoDataRepository;
    private EntityToResultMapper entityToResultMapper;
    private RequestToEntityMapper requestToEntityMapper;


    public OptimisticLockController(DemoDataRepository demoDataRepository, EntityToResultMapper entityToResultMapper, RequestToEntityMapper requestToEntityMapper) {
        this.demoDataRepository = demoDataRepository;
        this.entityToResultMapper = entityToResultMapper;
        this.requestToEntityMapper = requestToEntityMapper;
    }

    @GetMapping("/normal")
    public List<NormalResult> get() {
        return entityToResultMapper.entitiesToResults(demoDataRepository.findAll());
    }

    @GetMapping("/etag")
    public List<IncludeEtagResult> getEtag() {
        return entityToResultMapper.entitiesToIncludeEtagResults(demoDataRepository.findAll());
    }

    @Transactional
    @PutMapping("/etag/{id}")
    public void updateEtag(@PathVariable Integer id, @RequestBody UpdateRequestBody body, @RequestHeader("Etag") String etag) {

        DemoDataEntity prevDemoDataEntity = checkForOptimisticLock(id, etag);

        DemoDataEntity updateEntity = requestToEntityMapper.translate(body);
        updateEntity.setInsertTime(prevDemoDataEntity.getInsertTime());
        updateEntity.setId(id);

        demoDataRepository.save(updateEntity);
    }

    private DemoDataEntity checkForOptimisticLock(Integer id, String etag) {
        Optional<DemoDataEntity> dde = demoDataRepository.findById(id);

        if (dde.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (etag.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String calculatedEtag = getHash(dde.get().toString());
        if (!etag.equals(calculatedEtag)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return dde.get();
    }

    @GetMapping("/lastModify")
    public List<NormalResult> getLastModify(HttpServletResponse response) {
        response.addHeader("Last-Modified", String.valueOf(new Timestamp(System.currentTimeMillis())));
        return entityToResultMapper.entitiesToResults(demoDataRepository.findAll());
    }

    @Transactional
    @PutMapping("/lastModify/{id}")
    public void updateLastModify(@PathVariable Integer id, @RequestBody UpdateRequestBody body, @RequestHeader("If-Unmodified-Since") String ifUnmodifiedSince) {
        Timestamp ifUnmodifiedSinceTimestamp;
        try {
            ifUnmodifiedSinceTimestamp = Timestamp.valueOf(ifUnmodifiedSince);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal If-Unmodified-Since header", e);
        }
        DemoDataEntity prevDemoDataEntity = checkForOptimisticLockLastModify(id, ifUnmodifiedSinceTimestamp);

        DemoDataEntity updateEntity = requestToEntityMapper.translate(body);
        updateEntity.setInsertTime(prevDemoDataEntity.getInsertTime());
        updateEntity.setId(id);

        demoDataRepository.save(updateEntity);
    }

    private DemoDataEntity checkForOptimisticLockLastModify(Integer id, Timestamp ifUnmodifiedSince) {
        Optional<DemoDataEntity> dde = demoDataRepository.findById(id);

        if (dde.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (ifUnmodifiedSince == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Timestamp lastUpdateTime = dde.get().getUpdateTime();
        if (lastUpdateTime.after(ifUnmodifiedSince)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }

        return dde.get();
    }
}
