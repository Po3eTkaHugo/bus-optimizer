package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.entity.Coordinates;
import ru.busoptimizer.backend.service.BusesService;
import ru.busoptimizer.backend.service.CoordinatesService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class CoordinatesController {
    private final CoordinatesService coordinatesService;

    @GetMapping("/coordinates/{id}")
    public Coordinates getCoordinate(@PathVariable long id) {
        return coordinatesService.getCoordinate(id);
    }

    @GetMapping("/coordinates")
    public List<Coordinates> getCoordinates() {
        return coordinatesService.getAllCoordinates();
    }

    @PostMapping("/coordinates")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Coordinates addCoordinate(@RequestBody Coordinates coordinate) {
        return coordinatesService.saveCoordinate(coordinate);
    }

    @PutMapping("/coordinates/{id}")
    public Coordinates updateCoordinate(@PathVariable long id, @RequestBody Coordinates coordinate) {
        return coordinatesService.updateCoordinate(id, coordinate);
    }

    @DeleteMapping("/coordinates/{id}")
    public void deleteCoordinate(@PathVariable long id) {
        coordinatesService.deleteCoordinate(id);
    }
}
