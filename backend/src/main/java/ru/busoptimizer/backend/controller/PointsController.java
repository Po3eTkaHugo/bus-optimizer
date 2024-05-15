package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.service.PointsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class PointsController {
    private final PointsService pointsService;

    @GetMapping("/points/{id}")
    public Points getPoint(@PathVariable long id) {
        return pointsService.getPoint(id);
    }

    @GetMapping("/points")
    public List<Points> getPoints() {
        return pointsService.getAllPoints();
    }

    @PostMapping("/points")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Points addPoint(@RequestBody Points point) {
        return pointsService.savePoint(point);
    }

    @PutMapping("/points/{id}")
    public Points updatePoint(@PathVariable long id, @RequestBody Points point) {
        return pointsService.updatePoint(id, point);
    }

    @DeleteMapping("/points/{id}")
    public void deletePoint(@PathVariable long id) {
        pointsService.deletePoint(id);
    }
}
