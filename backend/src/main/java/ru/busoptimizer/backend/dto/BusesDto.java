package ru.busoptimizer.backend.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.busoptimizer.backend.entity.Buses}
 */
public record BusesDto(String name) implements Serializable {
}