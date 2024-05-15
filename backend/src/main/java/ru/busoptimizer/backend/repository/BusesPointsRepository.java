package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.BusesPoints;

public interface BusesPointsRepository extends JpaRepository<BusesPoints, Long> {
}