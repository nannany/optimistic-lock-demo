package nannany.optimistic.demo;

import nannany.optimistic.demo.controller.Result;
import nannany.optimistic.demo.infra.DemoDataEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityToResultMapper {

    Result translate(DemoDataEntity damoDataEntity);

    List<Result> entitiesToResults(List<DemoDataEntity> demoDataEntities);

}
