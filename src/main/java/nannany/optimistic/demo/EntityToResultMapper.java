package nannany.optimistic.demo;

import nannany.optimistic.demo.controller.IncludeEtagResult;
import nannany.optimistic.demo.controller.NormalResult;
import nannany.optimistic.demo.infra.DemoDataEntity;
import nannany.optimistic.demo.util.DemoUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Mapper(componentModel = "spring", imports = {DemoUtils.class, StandardCharsets.class})
public interface EntityToResultMapper {

    NormalResult translate(DemoDataEntity damoDataEntity);

    @Mapping(target = "etag", expression = "java( new String(DemoUtils.getHash(demoDataEntity.toString())) )")
    IncludeEtagResult translateToIncludeEtagResult(DemoDataEntity demoDataEntity);

    List<NormalResult> entitiesToResults(List<DemoDataEntity> demoDataEntities);

    List<IncludeEtagResult> entitiesToIncludeEtagResults(List<DemoDataEntity> demoDataEntities);

}
