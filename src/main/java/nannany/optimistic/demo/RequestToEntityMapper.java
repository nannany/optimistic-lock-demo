package nannany.optimistic.demo;

import nannany.optimistic.demo.controller.UpdateRequestBody;
import nannany.optimistic.demo.infra.DemoDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

@Mapper(componentModel = "spring", imports= Timestamp.class)
public interface RequestToEntityMapper {

    @Mapping(target = "updateTime", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "insertTime", expression = "java(new Timestamp(System.currentTimeMillis()))")
    DemoDataEntity translate(UpdateRequestBody updateRequestBody);
}
