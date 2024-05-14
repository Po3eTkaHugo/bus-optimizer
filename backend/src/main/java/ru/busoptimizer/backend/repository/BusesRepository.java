package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.Buses;

public interface BusesRepository extends JpaRepository<Buses, Long> {
}