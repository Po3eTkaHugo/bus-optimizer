package ru.busoptimizer.backend.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.busoptimizer.backend.entity.BusesPoints}
 */
public record BusesPointsDto(
        Long busesId,
        Long pointsId,
        Boolean direction) implements Serializable {
}