package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.dto.PointsDto;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.mapper.PointsMapperImpl;
import ru.busoptimizer.backend.service.PointsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class PointsController {
    private final PointsService pointsService;
    private final PointsMapperImpl pointsMapper;

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
    public Points addPoint(@RequestBody PointsDto pointDto) {
        return pointsService.savePoint(pointsMapper.toEntity(pointDto));
    }

    @PutMapping("/points/{id}")
    public Points updatePoint(@PathVariable long id, @RequestBody PointsDto pointDto) {
        return pointsService.updatePoint(id, pointsMapper.toEntity(pointDto));
    }

    @DeleteMapping("/points/{id}")
    public HttpStatus deletePoint(@PathVariable long id) {
        pointsService.deletePoint(id);
        return HttpStatus.OK;
    }
}
