package ru.busoptimizer.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.Stops;
import ru.busoptimizer.backend.exception_handler.NotFoundException;
import ru.busoptimizer.backend.repository.StopsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StopsService {
    private final StopsRepository stopsRepository;

    public List<Stops> getAllStops() {
        return stopsRepository.findAll();
    }

    public Stops saveStop(Stops stop) {
        return stopsRepository.save(stop);
    }

    public Stops getStop(long id) {
        return stopsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно найти данные. Остановка с id = " + id + " не найдена в базе данных."));
    }

    public Stops updateStop(long id, Stops newStop) {
        Stops stop = stopsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно обновить данные. Остановка с id = " + id + " не найдена в базе данных."));
        stop.setName(newStop.getName());

        return stopsRepository.save(stop);
    }

    public void deleteStop(long id) {
        Stops stop = stopsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно удалить данные. Остановка с id = " + id + " не найдена в базе данных."));
        if (stop != null) {
            stopsRepository.deleteById(id);
        }
    }

}
