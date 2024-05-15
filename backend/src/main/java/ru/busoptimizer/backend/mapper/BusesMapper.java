package ru.busoptimizer.backend.mapper;

import org.mapstruct.*;
import ru.busoptimizer.backend.dto.BusesDto;
import ru.busoptimizer.backend.entity.Buses;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusesMapper {
    Buses toEntity(BusesDto busesDto);

    BusesDto toDto(Buses buses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Buses partialUpdate(BusesDto busesDto, @MappingTarget Buses buses);
}