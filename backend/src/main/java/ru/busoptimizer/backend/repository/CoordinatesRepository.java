package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.Coordinates;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}