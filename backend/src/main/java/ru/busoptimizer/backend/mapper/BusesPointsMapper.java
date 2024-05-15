package ru.busoptimizer.backend.mapper;

import org.mapstruct.*;
import ru.busoptimizer.backend.dto.BusesPointsDto;
import ru.busoptimizer.backend.entity.BusesPoints;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusesPointsMapper {
    @Mapping(source = "pointsId", target = "points.id")
    @Mapping(source = "busesId", target = "buses.id")
    BusesPoints toEntity(BusesPointsDto busesPointsDto);

    @InheritInverseConfiguration(name = "toEntity")
    BusesPointsDto toDto(BusesPoints busesPoints);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BusesPoints partialUpdate(BusesPointsDto busesPointsDto, @MappingTarget BusesPoints busesPoints);
}