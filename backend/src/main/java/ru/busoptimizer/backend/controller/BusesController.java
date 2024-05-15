package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.dto.BusesDto;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.mapper.BusesMapperImpl;
import ru.busoptimizer.backend.service.BusesService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class BusesController {
    private final BusesService busesService;
    private final BusesMapperImpl busesMapper;

    @GetMapping("/buses/{id}")
    public Buses getBus(@PathVariable long id) {
        return busesService.getBus(id);
    }

    @GetMapping("/buses")
    public List<Buses> getBus() {
        return busesService.getAllBuses();
    }

    @PostMapping("/buses")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Buses addBus(@RequestBody BusesDto busDto) {
        return busesService.saveBus(busesMapper.toEntity(busDto));
    }

    @PutMapping("/buses/{id}")
    public Buses updateBus(@PathVariable long id, @RequestBody BusesDto busDto) {
        return busesService.updateBus(id, busesMapper.toEntity(busDto));
    }

    @DeleteMapping("/buses/{id}")
    public HttpStatus deleteBus(@PathVariable long id) {
        busesService.deleteBus(id);
        return HttpStatus.OK;
    }
}
