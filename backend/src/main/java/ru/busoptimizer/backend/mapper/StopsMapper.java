package ru.busoptimizer.backend.mapper;

import org.mapstruct.*;
import ru.busoptimizer.backend.dto.StopsDto;
import ru.busoptimizer.backend.entity.Stops;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StopsMapper {
    Stops toEntity(StopsDto stopsDto);

    StopsDto toDto(Stops stops);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Stops partialUpdate(StopsDto stopsDto, @MappingTarget Stops stops);
}