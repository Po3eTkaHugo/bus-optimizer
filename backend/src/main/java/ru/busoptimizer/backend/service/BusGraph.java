package ru.busoptimizer.backend.service;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.BusesPoints;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.repository.BusesPointsRepository;
import ru.busoptimizer.backend.repository.BusesRepository;
import ru.busoptimizer.backend.repository.PointsRepository;
import ru.busoptimizer.backend.repository.StopsRepository;

import java.util.*;
import static ru.busoptimizer.backend.service.Distance.calcDistance;

@RequiredArgsConstructor
@Service
public class BusGraph {
    private final StopsRepository stopsRepository;
    private final BusesPointsRepository busesPointsRepository;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Node {
        boolean exist;
        List<String> busNames = new ArrayList<>();
        double distance;
    }

    List<List<Node>> busGraph = new ArrayList<>();
    private final BusesRepository busesRepository;
    private final PointsRepository pointsRepository;

    public void fillingGraph() {
        //Инициализация матрицы
        long countOfStops = stopsRepository.count();

        for (int i = 0; i < countOfStops; i++) {
            busGraph.add(new ArrayList<>());
            for (int j = 0; j < countOfStops; j++) {
                busGraph.get(i).add(new Node());
            }
        }

        //Заполнение матрицы
        /*long k = 1;
        List<BusesPoints> busesPoints = busesPointsRepository.findByBuses_Id(k);
        System.out.println(busesPoints.get(0).getPoints().getStops().getId());*/

        long countBus = busesRepository.count();
        for (long i = 0; i < countBus; i++) {
            List<BusesPoints> busesPoints = busesPointsRepository.findByBuses_Id(i + 1);
            for(int k = 0; k < busesPoints.size() - 1; k++) {
                if (busesPoints.get(k).getDirection() != busesPoints.get(k + 1).getDirection()) {
                    continue;
                }

                int startId = Math.toIntExact(busesPoints.get(k).getPoints().getStops().getId()) - 1;
                int endId = Math.toIntExact(busesPoints.get(k + 1).getPoints().getStops().getId()) - 1;
                busGraph.get(startId).get(endId).setExist(true);
                busGraph.get(startId).get(endId).busNames.add(busesPoints.get(k).getBuses().getName());
            }
        }
    }

    public List<Integer> bfs(int s) {
        List<Integer> farStops = new ArrayList<>();

        boolean[] visited = new boolean[(int) stopsRepository.count()];
        Queue<Integer> queue = new LinkedList<>();
        List<String> startBusNames = new ArrayList<>();

        visited[s] = true;
        queue.add(s);

        boolean firstStep = true;

        while (!queue.isEmpty()) {
            Integer p = queue.poll();

            if (firstStep) {
                for(int i = 0; i < stopsRepository.count(); i++) {
                    if (busGraph.get(p).get(i).exist) {
                        startBusNames.addAll(busGraph.get(p).get(i).busNames);
                    }
                }
                firstStep = false;
            }

            for(int i = 0; i < stopsRepository.count(); i++) {
                if (!visited[i] && busGraph.get(p).get(i).exist) {
                    visited[i] = true;
                    if (busGraph.get(p).get(i).busNames.containsAll(startBusNames)) {
                        queue.add(i);
                    }
                    else {
                        farStops.add(i);
                    }
                }
            }
        }

        return farStops;
    }

    public List<List<Double>> findFarStops() {
        List<List<Double>> Segments = new ArrayList<>();

        for(int i = 0; i < busesRepository.count(); i++) {

            List<BusesPoints> busesPoints = busesPointsRepository.findByBuses_Id((long) (i + 1));
            List<Integer> farStops = bfs((int) (busesPoints.get(0).getPoints().getStops().getId() - 1));

            for (Integer farStop : farStops) {
                List<Integer> backFarStops = bfs(farStop);
                System.out.println(farStop);
                System.out.println(backFarStops.toString());

                double minDist = 1000.0;
                double minN1 = 0.0;
                double minE1 = 0.0;
                double minN2 = 0.0;
                double minE2 = 0.0;

                List<Points> farPoints = pointsRepository.findByStops_Id(Long.valueOf(farStop) + 1);
                for (Integer backStop : backFarStops) { //лишний???
                    List<Points> backPoints = pointsRepository.findByStops_Id(Long.valueOf(backStop) + 1);


                    for(Points farPoint : farPoints) {
                        for(Points backPoint : backPoints){
                           double calcDist = calcDistance(farPoint.getN_latitude(), farPoint.getE_longitude(), backPoint.getN_latitude(), backPoint.getE_longitude());
                           if (calcDist < minDist) {
                               minDist = calcDist;
                               minN1 = backPoint.getN_latitude();
                               minE1 = backPoint.getE_longitude();
                               minN2 = farPoint.getN_latitude();
                               minE2 = farPoint.getE_longitude();
                           }
                        }
                    }
                }

                if (minDist <= 1.0) {
                    System.out.println(minDist);
                    System.out.println(minN1 + " " + minE1 + " " + minN2 + " " + minE2);
                    boolean exist = false;
                    if (!Segments.isEmpty()) {
                        for(List<Double> ansSegment : Segments) {
                            if ((ansSegment.get(0) == minN1 && ansSegment.get(1) == minE1 && ansSegment.get(2) == minN2 && ansSegment.get(3) == minE2) ||
                                    (ansSegment.get(0) == minN2 && ansSegment.get(1) == minE2 && ansSegment.get(2) == minN1 && ansSegment.get(3) == minE1)) {
                                exist = true;
                                break;
                            }
                        }
                    }
                    if (!exist) {
                        List<Double> segment = new ArrayList<>();
                        segment.add(minN1);
                        segment.add(minE1);
                        segment.add(minN2);
                        segment.add(minE2);
                        Segments.add(segment);
                    }
                }
            }
        }

        return Segments;
    }
}
