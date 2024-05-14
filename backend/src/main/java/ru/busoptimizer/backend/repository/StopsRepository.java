package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.Stops;

public interface StopsRepository extends JpaRepository<Stops, Long> {
}