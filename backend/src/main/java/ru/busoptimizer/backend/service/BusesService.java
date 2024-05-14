package ru.busoptimizer.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.exception_handler.NotFoundException;
import ru.busoptimizer.backend.repository.BusesRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BusesService {
    private final BusesRepository busesRepository;

    public List<Buses> getAllBuses() {
        return busesRepository.findAll();
    }

    public Buses saveBus(Buses bus) {
        return busesRepository.save(bus);
    }

    public Buses getBus(long id) {
        return busesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно найти данные. Автобус с id = " + id + " не найден в базе данных."));
    }

    public Buses updateBus(long id, Buses newBus) {
        Buses bus = busesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно обновить данные. Автобус с id = " + id + " не найден в базе данных."));
        bus.setName(newBus.getName());

        return busesRepository.save(bus);
    }

    public void deleteBus(long id) {
        Buses bus = busesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Невозможно удалить данные. Автобус с id = " + id + " не найден в базе данных."));
        if (bus != null) {
            busesRepository.deleteById(id);
        }
    }

}
