package nannany.optimistic.demo.controller;

import com.google.common.hash.Hashing;
import nannany.optimistic.demo.EntityToResultMapper;
import nannany.optimistic.demo.RequestToEntityMapper;
import nannany.optimistic.demo.infra.DemoDataEntity;
import nannany.optimistic.demo.infra.DemoDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

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
    public void update(@PathVariable Integer id, @RequestBody UpdateRequestBody body, @RequestHeader("Etag") String etag) {

        checkForOptimisticLock(id, etag);

        DemoDataEntity updateEntity = requestToEntityMapper.translate(body);
        updateEntity.setId(id);

        demoDataRepository.save(updateEntity);
    }

    private void checkForOptimisticLock(Integer id, String etag) {
        Optional<DemoDataEntity> dde = demoDataRepository.findById(id);

        if (dde.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (etag.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        @SuppressWarnings("UnstableApiUsage")
        var calculatedEtag = Hashing.sha256().hashString(dde.get().toString(), UTF_8).toString();
        if (!etag.equals(calculatedEtag)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
