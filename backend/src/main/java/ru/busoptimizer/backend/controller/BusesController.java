package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.dto.BusesDto;
import ru.busoptimizer.backend.mapper.BusesMapperImpl;
import ru.busoptimizer.backend.service.BusesService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class BusesController {
    private final BusesService busesService;

    @Autowired
    private BusesMapperImpl busesMapper;

    @GetMapping("/buses/{id}")
    public BusesDto getBus(@PathVariable long id) {
        return busesMapper.toDto(busesService.getBus(id));
    }

    @GetMapping("/buses")
    public List<BusesDto> getBus() {
        return busesService.getAllBuses().stream().map(busesMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/buses")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BusesDto addBus(@RequestBody BusesDto busDto) {
        return busesMapper.toDto(busesService.saveBus(busesMapper.toEntity(busDto)));
    }

    @PutMapping("/buses/{id}")
    public BusesDto updateBus(@PathVariable long id, @RequestBody BusesDto busDto) {
        return busesMapper.toDto(busesService.updateBus(id, busesMapper.toEntity(busDto)));
    }

    @DeleteMapping("/buses/{id}")
    public void deleteBus(@PathVariable long id) {
        busesService.deleteBus(id);
    }
}
