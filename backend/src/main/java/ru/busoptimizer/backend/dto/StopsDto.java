package ru.busoptimizer.backend.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.busoptimizer.backend.entity.Stops}
 */
public record StopsDto(
        String name) implements Serializable {
}