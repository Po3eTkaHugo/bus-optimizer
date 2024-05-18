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

import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

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
        /*List<Points> stopsPoints = pointsRepository.findByStops_Id(point.getStops().getId());

        if (!stopsPoints.isEmpty()) {
            for(Points stopPoint : stopsPoints) {
                double distance = calcDistance(point.getN_latitude(), point.getE_longitude(), stopPoint.getN_latitude(), stopPoint.getE_longitude());

                if (distance > 0.5) {
                    String mainName = stopPoint.getStops().getName();
                    List<Stops> allOtherStops = stopsRepository.findByNameContaining(mainName);

                    allOtherStops.forEach(otherStop -> {
                        List<Points> otherPoints = pointsRepository.findByStops_Id(otherStop.getId());
                        otherPoints.forEach(otherPoint -> {
                            double otherDistance = calcDistance(point.getN_latitude(), point.getE_longitude(), otherPoint.getN_latitude(), otherPoint.getE_longitude());

                            if (otherDistance > 0.5) {
                                Stops newStop = stopsService.saveStop(stopsMapper.toEntity(new StopsDto(
                                        mainName + " - " + stopsRepository.countByNameContaining(mainName))));

                                pointsRepository.save(pointsMapper.toEntity(new PointsDto(
                                        point.getN_latitude(),
                                        point.getE_longitude(),
                                        newStop.getId())));
                            }
                        });
                    });
                }
            }
        }*/
        List<Points> stopsPoints = pointsRepository.findByStops_Id(point.getStops().getId());
        if (!stopsPoints.isEmpty()) {
            String mainName = stopsRepository.getById(point.getStops().getId()).getName();

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

    private double calcDistance(double NLat1, double ELong1, double NLat2, double ELong2) {
        double N1 = toRadians(NLat1);
        double E1 = toRadians(ELong1);
        double N2 = toRadians(NLat2);
        double E2 = toRadians(ELong2);

        double u = sin((N2 - N1) / 2.0);
        double v = sin((E2 - E1) / 2.0);
        return 6371.0 * 2.0 * asin(sqrt(u * u + cos(N1) * cos(N2) * v * v));
    }

}
