package ru.busoptimizer.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.exception_handler.NotFoundException;
import ru.busoptimizer.backend.repository.PointsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PointsService {
    private final PointsRepository pointsRepository;

    public List<Points> getAllPoints() {
        return pointsRepository.findAll();
    }

    public Points savePoint(Points point) {
        return pointsRepository.save(point);
    }

    public Points getPoint(long id) {
        return pointsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно найти данные. Точка с id = " + id + " не найдена в базе данных."));
    }

    public Points updatePoint(long id, Points newPoint) {
        Points point = pointsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно обновить данные. Точка с id = " + id + " не найдена в базе данных."));
        point.setStops(newPoint.getStops());
        point.setN_latitude(newPoint.getN_latitude());
        point.setE_longitude(newPoint.getE_longitude());

        return pointsRepository.save(point);
    }

    public void deletePoint(long id) {
        Points point = pointsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно удалить данные. Точка с id = " + id + " не найдена в базе данных."));
        if (point != null) {
            pointsRepository.deleteById(id);
        }
    }

}
