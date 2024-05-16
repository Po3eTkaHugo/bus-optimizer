package ru.busoptimizer.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.busoptimizer.backend.dto.StopsDto;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.entity.Stops;
import ru.busoptimizer.backend.mapper.StopsMapper;
import ru.busoptimizer.backend.mapper.StopsMapperImpl;
import ru.busoptimizer.backend.service.StopsService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class StopsController {
    private final StopsService stopsService;
    private final StopsMapperImpl stopsMapper;

    @GetMapping("/stops/{id}")
    public StopsDto getStop(@PathVariable long id) {
        return stopsMapper.toDto(stopsService.getStop(id));
    }

    @GetMapping("/stops")
    public List<Stops> getStop() {
        return stopsService.getAllStops();
    }

    @PostMapping("/stops")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Stops addStop(@RequestBody StopsDto stopDto) {
        return stopsService.saveStop(stopsMapper.toEntity(stopDto));
    }

    @PutMapping("/stops/{id}")
    public Stops updateStops(@PathVariable long id, @RequestBody StopsDto stopDto) {
        return stopsService.updateStop(id, stopsMapper.toEntity(stopDto));
    }

    @DeleteMapping("/stops/{id}")
    public HttpStatus deleteStop(@PathVariable long id) {
        stopsService.deleteStop(id);
        return HttpStatus.OK;
    }
}
