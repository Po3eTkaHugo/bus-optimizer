package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.dto.BusesDto;
import ru.busoptimizer.backend.dto.BusesPointsDto;
import ru.busoptimizer.backend.dto.PointsDto;
import ru.busoptimizer.backend.dto.StopsDto;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.entity.BusesPoints;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.entity.Stops;
import ru.busoptimizer.backend.mapper.BusesMapperImpl;
import ru.busoptimizer.backend.mapper.BusesPointsMapperImpl;
import ru.busoptimizer.backend.mapper.PointsMapperImpl;
import ru.busoptimizer.backend.mapper.StopsMapperImpl;
import ru.busoptimizer.backend.service.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class DataController {
    private final BusesService busesService;
    private final BusesMapperImpl busesMapper;
    private final StopsService stopsService;
    private final StopsMapperImpl stopsMapper;
    private final PointsService pointsService;
    private final PointsMapperImpl pointsMapper;
    private final BusesPointsService busesPointsService;
    private final BusesPointsMapperImpl busesPointsMapper;
    private final BusGraph busGraph;

    @PostMapping("/insert")
    @ResponseStatus(code = HttpStatus.CREATED)
    public HttpStatus addBusTest(@RequestBody Map<String, Object> json) {
        String busName = (String) json.get("name");                                        //Название Автобуса

        Buses bus = busesService.saveBus(busesMapper.toEntity(new BusesDto(busName)));

        List<Object> bothDirections = (List<Object>) json.get("features");

        for (int k = 0; k < 2; k++) {
            Map<String, Object> forwardDirection = (Map<String, Object>) bothDirections.get(k);
            List<Map<String, Object>> allPoints = (List<Map<String, Object>>) forwardDirection.get("features");

            for(int i = 0; i < allPoints.size(); i+= 2) {
                Map<String, Object> firstPoint = allPoints.get(i);
                String stopName = (String) firstPoint.get("name");                              //Название Остановки
                List<Double> pointCoordinates = (List<Double>) firstPoint.get("coordinates");   //Координаты точки остановки

                Stops stop = stopsService.saveStop(stopsMapper.toEntity(new StopsDto(stopName)));

                Points point = pointsService.savePoint(pointsMapper.toEntity(new PointsDto(
                        pointCoordinates.get(1),
                        pointCoordinates.get(0),
                        stop.getId())));

                BusesPoints busPoint = busesPointsService.saveBusPoint(busesPointsMapper.toEntity(new BusesPointsDto(
                        bus.getId(),
                        point.getId(),
                        k == 1)));
            }

        }

        return HttpStatus.OK;
    }

    @GetMapping("/graph/create_graph")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGraph() {
        busGraph.fillingGraph();
    }

    @GetMapping("/graph/far_stops")
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<List<Double>> startScanning() {
        return busGraph.findFarStops();
    }

    @GetMapping("/find_far_stops")
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<List<Double>> findFarStops() {
        busGraph.fillingGraph();
        return busGraph.findFarStops();
    }

}
