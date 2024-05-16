package ru.busoptimizer.backend.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.busoptimizer.backend.entity.Points}
 */
public record PointsDto(
        Double n_latitude,
        Double e_longitude,
        Long stopsId) implements Serializable {
}