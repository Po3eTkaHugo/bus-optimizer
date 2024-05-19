package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.BusesPoints;

import java.util.List;
import java.util.Optional;

public interface BusesPointsRepository extends JpaRepository<BusesPoints, Long> {


    List<BusesPoints> findByBuses_Id(Long id);
}