package ru.busoptimizer.backend.mapper;

import org.mapstruct.*;
import ru.busoptimizer.backend.dto.PointsDto;
import ru.busoptimizer.backend.entity.Points;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {StopsMapper.class})
public interface PointsMapper {
    @Mapping(source = "stopsId", target = "stops.id")
    Points toEntity(PointsDto pointsDto);

    @Mapping(source = "stops.id", target = "stopsId")
    PointsDto toDto(Points points);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Points partialUpdate(PointsDto pointsDto, @MappingTarget Points points);
}