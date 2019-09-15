package nannany.optimistic.demo.controller;

import nannany.optimistic.demo.EntityToResultMapper;
import nannany.optimistic.demo.infra.DemoDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("optimistic")
public class OptimisticLockController {

    private DemoDataRepository demoDataRepository;
    private EntityToResultMapper entityToResultMapper;

    Logger logger = LoggerFactory.getLogger(OptimisticLockController.class);

    public OptimisticLockController(DemoDataRepository demoDataRepository, EntityToResultMapper entityToResultMapper) {
        this.demoDataRepository = demoDataRepository;
        this.entityToResultMapper = entityToResultMapper;
    }

    @GetMapping("/normal")
    public List<NormalResult> get() {
        return entityToResultMapper.entitiesToResults(demoDataRepository.findAll());
    }

    @GetMapping("/etag")
    public List<IncludeEtagResult> getEtag() {
        return entityToResultMapper.entitiesToIncludeEtagResults(demoDataRepository.findAll());
    }
}
