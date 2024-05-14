package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.entity.Stops;
import ru.busoptimizer.backend.service.StopsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class StopsController {
    private final StopsService stopsService;

    @GetMapping("/stops/{id}")
    public Stops getStop(@PathVariable long id) {
        return stopsService.getStop(id);
    }

    @GetMapping("/stops")
    public List<Stops> getStop() {
        return stopsService.getAllStops();
    }

    @PostMapping("/stops")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Stops addStop(@RequestBody Stops stop) {
        return stopsService.saveStop(stop);
    }

    @PutMapping("/stops/{id}")
    public Stops updateStops(@PathVariable long id, @RequestBody Stops stop) {
        return stopsService.updateStop(id, stop);
    }

    @DeleteMapping("/stops/{id}")
    public void deleteStop(@PathVariable long id) {
        stopsService.deleteStop(id);
    }
}
