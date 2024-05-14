package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.BusesStops;

public interface BusesStopsRepository extends JpaRepository<BusesStops, Long> {
}