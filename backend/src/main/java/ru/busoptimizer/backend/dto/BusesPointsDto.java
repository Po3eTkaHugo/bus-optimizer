package ru.busoptimizer.backend.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.busoptimizer.backend.entity.BusesPoints}
 */
public record BusesPointsDto(
        Long busesId,
        Long pointsId,
        Boolean forward_direction,
        Boolean reverse_direction) implements Serializable {
}