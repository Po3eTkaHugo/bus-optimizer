package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.entity.BusesStops;
import ru.busoptimizer.backend.service.BusesStopsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class BusesStopsController {
    private final BusesStopsService busesStopsService;

    @GetMapping("/buses_stops/{id}")
    public BusesStops getBusStop(@PathVariable long id) {
        return busesStopsService.getBusStop(id);
    }

    @GetMapping("/buses_stops")
    public List<BusesStops> getBusStop() {
        return busesStopsService.getAllBusesStops();
    }

    @PostMapping("/buses_stops")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BusesStops addBus(@RequestBody BusesStops busStop) {
        return busesStopsService.saveBusStop(busStop);
    }

    @PutMapping("/buses_stops/{id}")
    public BusesStops updateBusStop(@PathVariable long id, @RequestBody BusesStops busStop) {
        return busesStopsService.updateBusStop(id, busStop);
    }

    @DeleteMapping("/buses_stops/{id}")
    public void deleteBusStop(@PathVariable long id) {
        busesStopsService.deleteBusStop(id);
    }
}
