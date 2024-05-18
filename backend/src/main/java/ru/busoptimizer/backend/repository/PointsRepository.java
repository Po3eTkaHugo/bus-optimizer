package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.Points;

import java.util.List;

public interface PointsRepository extends JpaRepository<Points, Long> {

    List<Points> findByStops_Id(Long id);
}