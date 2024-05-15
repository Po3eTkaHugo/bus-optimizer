package ru.busoptimizer.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.Coordinates;
import ru.busoptimizer.backend.entity.Stops;
import ru.busoptimizer.backend.exception_handler.NotFoundException;
import ru.busoptimizer.backend.repository.CoordinatesRepository;
import ru.busoptimizer.backend.repository.StopsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;

    public List<Coordinates> getAllCoordinates() {
        return coordinatesRepository.findAll();
    }

    public Coordinates saveCoordinate(Coordinates coordinate) {
        return coordinatesRepository.save(coordinate);
    }

    public Coordinates getCoordinate(long id) {
        return coordinatesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно найти данные. Остановка с id = " + id + " не найден в базе данных."));
    }

    public Coordinates updateCoordinate(long id, Coordinates newCoordinate) {
        Coordinates coordinate = coordinatesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно обновить данные. Автобус с id = " + id + " не найден в базе данных."));
        coordinate.setN_latitude(newCoordinate.getN_latitude());
        coordinate.setE_longitude(newCoordinate.getE_longitude());

        return coordinatesRepository.save(coordinate);
    }

    public void deleteCoordinate(long id) {
        Coordinates coordinate = coordinatesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно удалить данные. Автобус с id = " + id + " не найден в базе данных."));
        if (coordinate != null) {
            coordinatesRepository.deleteById(id);
        }
    }

}
