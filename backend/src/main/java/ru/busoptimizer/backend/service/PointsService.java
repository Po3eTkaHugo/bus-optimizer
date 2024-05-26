package ru.busoptimizer.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.dto.PointsDto;
import ru.busoptimizer.backend.dto.StopsDto;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.entity.Stops;
import ru.busoptimizer.backend.exception_handler.NotFoundException;
import ru.busoptimizer.backend.mapper.PointsMapperImpl;
import ru.busoptimizer.backend.mapper.StopsMapperImpl;
import ru.busoptimizer.backend.repository.PointsRepository;
import ru.busoptimizer.backend.repository.StopsRepository;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static ru.busoptimizer.backend.service.Distance.calcDistance;

@RequiredArgsConstructor
@Service
public class PointsService {
    private final PointsRepository pointsRepository;
    private final StopsRepository stopsRepository;
    private final StopsService stopsService;
    private final StopsMapperImpl stopsMapper;
    private final PointsMapperImpl pointsMapper;

    public List<Points> getAllPoints() {
        return pointsRepository.findAll();
    }

    public Points savePoint(Points point) {
        List<Points> stopsPoints = pointsRepository.findByStops_Id(point.getStops().getId());
        if (!stopsPoints.isEmpty()) {
            String mainName = stopsRepository.getById(point.getStops().getId()).getName(); //Заменить на findNameById

            boolean findHerStop = false;
            long idHerStop = point.getStops().getId();

            List<Stops> allStops = stopsRepository.findByNameContaining(mainName);
            for (Stops stop : allStops) {
                List<Points> otherPoints = pointsRepository.findByStops_Id(stop.getId());

                for(Points otherPoint : otherPoints) {
                    double otherDistance = calcDistance(point.getN_latitude(), point.getE_longitude(), otherPoint.getN_latitude(), otherPoint.getE_longitude());

                    if (otherDistance <= 0.5) {
                        findHerStop = true;
                        break;
                    }
                }

                if (findHerStop) {
                    idHerStop = stop.getId();
                    break;
                }
            }

            if (!findHerStop) {
                Stops newStop = stopsService.saveStop(stopsMapper.toEntity(new StopsDto(
                        mainName + " - " + stopsRepository.countByNameContaining(mainName))));
                idHerStop = newStop.getId();
            }
            else {
                List<Points> existingPoints = pointsRepository.findByStops_Id(idHerStop);

                for (Points existPoint : existingPoints) {
                    if (Objects.equals(point.getN_latitude(), existPoint.getN_latitude()) && Objects.equals(point.getE_longitude(), existPoint.getE_longitude())) {
                        return existPoint;
                    }
                }
            }


            return pointsRepository.save(pointsMapper.toEntity(new PointsDto(
                    point.getN_latitude(),
                    point.getE_longitude(),
                    idHerStop)));
        }

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
