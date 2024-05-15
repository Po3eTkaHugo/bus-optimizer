package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.Points;

public interface PointsRepository extends JpaRepository<Points, Long> {
}