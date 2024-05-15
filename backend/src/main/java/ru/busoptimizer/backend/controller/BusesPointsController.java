package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.entity.BusesPoints;
import ru.busoptimizer.backend.service.BusesPointsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class BusesPointsController {
    private final BusesPointsService busesPointsService;

    @GetMapping("/buses_points/{id}")
    public BusesPoints getBusPoint(@PathVariable long id) {
        return busesPointsService.getBusPoint(id);
    }

    @GetMapping("/buses_points")
    public List<BusesPoints> getBusPoint() {
        return busesPointsService.getAllBusesPoints();
    }

    @PostMapping("/buses_points")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BusesPoints addBusPoint(@RequestBody BusesPoints busPoint) {
        return busesPointsService.saveBusPoint(busPoint);
    }

    @PutMapping("/buses_points/{id}")
    public BusesPoints updateBusPoint(@PathVariable long id, @RequestBody BusesPoints busPoint) {
        return busesPointsService.updateBusPoint(id, busPoint);
    }

    @DeleteMapping("/buses_points/{id}")
    public void deleteBusPoint(@PathVariable long id) {
        busesPointsService.deleteBusPoint(id);
    }
}
