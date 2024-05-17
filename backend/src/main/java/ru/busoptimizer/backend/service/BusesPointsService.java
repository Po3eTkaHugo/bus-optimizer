package ru.busoptimizer.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.BusesPoints;
import ru.busoptimizer.backend.exception_handler.NotFoundException;
import ru.busoptimizer.backend.repository.BusesPointsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BusesPointsService {
    private final BusesPointsRepository busesPointsRepository;

    public List<BusesPoints> getAllBusesPoints() {
        return busesPointsRepository.findAll();
    }

    public BusesPoints saveBusPoint(BusesPoints busPoint) {
        return busesPointsRepository.save(busPoint);
    }

    public BusesPoints getBusPoint(long id) {
        return busesPointsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно найти данные. Связи автобуса и остановки с id = " + id + " не найдено в базе данных."));
    }

    public BusesPoints updateBusPoint(long id, BusesPoints newBusPoint) {
        BusesPoints busPoint = busesPointsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно обновить данные. Связи автобуса и остановки с id = " + id + " не найдено в базе данных."));
        busPoint.setBuses(newBusPoint.getBuses());
        busPoint.setPoints(newBusPoint.getPoints());
        busPoint.setDirection(newBusPoint.getDirection());

        return busesPointsRepository.save(busPoint);
    }

    public void deleteBusPoint(long id) {
        BusesPoints busPoint = busesPointsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно удалить данные. Связи автобуса и остановки с id = " + id + " не найдено в базе данных."));
        if (busPoint != null) {
            busesPointsRepository.deleteById(id);
        }
    }

}
