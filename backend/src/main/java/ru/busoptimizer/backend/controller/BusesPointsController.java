package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.dto.BusesPointsDto;
import ru.busoptimizer.backend.entity.BusesPoints;
import ru.busoptimizer.backend.mapper.BusesMapperImpl;
import ru.busoptimizer.backend.mapper.BusesPointsMapper;
import ru.busoptimizer.backend.mapper.BusesPointsMapperImpl;
import ru.busoptimizer.backend.service.BusesPointsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class BusesPointsController {
    private final BusesPointsService busesPointsService;
    private final BusesPointsMapperImpl busesMapper;

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
    public BusesPoints addBusPoint(@RequestBody BusesPointsDto busPointDto) {
        return busesPointsService.saveBusPoint(busesMapper.toEntity(busPointDto));
    }

    @PutMapping("/buses_points/{id}")
    public BusesPoints updateBusPoint(@PathVariable long id, @RequestBody BusesPointsDto busPointDto) {
        return busesPointsService.updateBusPoint(id, busesMapper.toEntity(busPointDto));
    }

    @DeleteMapping("/buses_points/{id}")
    public HttpStatus deleteBusPoint(@PathVariable long id) {
        busesPointsService.deleteBusPoint(id);
        return HttpStatus.OK;
    }
}
