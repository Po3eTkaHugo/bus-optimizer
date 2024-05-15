package ru.busoptimizer.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.BusesStops;
import ru.busoptimizer.backend.exception_handler.NotFoundException;
import ru.busoptimizer.backend.repository.BusesStopsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BusesStopsService {
    private final BusesStopsRepository busesStopsRepository;

    public List<BusesStops> getAllBusesStops() {
        return busesStopsRepository.findAll();
    }

    public BusesStops saveBusStop(BusesStops busStop) {
        return busesStopsRepository.save(busStop);
    }

    public BusesStops getBusStop(long id) {
        return busesStopsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно найти данные. Связи автобуса и остановки с id = " + id + " не найдено в базе данных."));
    }

    public BusesStops updateBusStop(long id, BusesStops newBusStop) {
        BusesStops busStop = busesStopsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно обновить данные. Связи автобуса и остановки с id = " + id + " не найдено в базе данных."));
        busStop.setBuses(newBusStop.getBuses());
        busStop.setStops(newBusStop.getStops());
        busStop.setForward_direction(newBusStop.getForward_direction());
        busStop.setReverse_direction(newBusStop.getReverse_direction());

        return busesStopsRepository.save(busStop);
    }

    public void deleteBusStop(long id) {
        BusesStops busStop = busesStopsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно удалить данные. Связи автобуса и остановки с id = " + id + " не найдено в базе данных."));
        if (busStop != null) {
            busesStopsRepository.deleteById(id);
        }
    }

}
