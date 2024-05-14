package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.service.BusesService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class BusesController {
    private final BusesService busesService;

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
    public Buses addBus(@RequestBody Buses bus) {
        return busesService.saveBus(bus);
    }

    @PutMapping("/buses/{id}")
    public Buses updateBus(@PathVariable long id, @RequestBody Buses bus) {
        return busesService.updateBus(id, bus);
    }

    @DeleteMapping("/buses/{id}")
    public void deleteBus(@PathVariable long id) {
        busesService.deleteBus(id);
    }
}
