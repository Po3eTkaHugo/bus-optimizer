package ru.busoptimizer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.entity.Stops;

import java.util.List;
import java.util.Optional;

public interface StopsRepository extends JpaRepository<Stops, Long> {

    Stops findByName(String name);

    List<Stops> findByNameContaining(String name);

    long countByNameContaining(String name);



}