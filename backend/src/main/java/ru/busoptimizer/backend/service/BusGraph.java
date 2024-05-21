package ru.busoptimizer.backend.service;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.Buses;
import ru.busoptimizer.backend.entity.BusesPoints;
import ru.busoptimizer.backend.entity.Points;
import ru.busoptimizer.backend.entity.Stops;
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

    public List<Integer> bfs(int s, Set<String> bfsBusNames) {
        List<Integer> farStops = new ArrayList<>();

        boolean[] visited = new boolean[(int) stopsRepository.count()];
        Queue<Integer> queue = new LinkedList<>();

        visited[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            Integer p = queue.poll();

            for(int i = 0; i < stopsRepository.count(); i++) {
                if (!visited[i] && busGraph.get(p).get(i).exist) {
                    visited[i] = true;

                    boolean founded = false;
                    for (String name : bfsBusNames) {
                        if (busGraph.get(p).get(i).busNames.contains(name)) {
                            founded = true;
                            break;
                        }
                    }
                    if (founded) {
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

    public List<List<List<Double>>> findFarStops() {
        List<List<List<Double>>> Segments = new ArrayList<>();

        for(int i = 0; i < busesRepository.count(); i++) {

            List<BusesPoints> busesPoints = busesPointsRepository.findByBuses_Id((long) (i + 1));
            Set<String> startBusName = new HashSet<>();
            startBusName.add(busesRepository.getById((long) (i + 1)).getName());
            List<Integer> farStops = bfs((int) (busesPoints.getLast().getPoints().getStops().getId() - 1), startBusName);

            System.out.println("StartBus = " + busesRepository.getById((long) (i + 1)).getName());
            for (Integer farStop : farStops) {
                Set<String> busesNames = new HashSet<>();
                stopsRepository.getById(Long.valueOf(farStop) + 1).getCoordinates().forEach(point -> point.getBusesPoints().forEach(busesPoint -> busesNames.add(busesPoint.getBuses().getName())));

                double minDist = 1000.0;
                double minN1 = 0.0;
                double minE1 = 0.0;
                double minN2 = 0.0;
                double minE2 = 0.0;

                    List<Integer> backFarStops = bfs(farStop, busesNames);
                    //System.out.println("FarBus = " + busName);
                    System.out.println(farStop);
                    System.out.println(backFarStops.toString());


                    List<Points> farPoints = pointsRepository.findByStops_Id(Long.valueOf(farStop) + 1);
                    for (Integer backStop : backFarStops) {
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
                    boolean exist = false;
                    if (!Segments.isEmpty()) {
                        for(List<List<Double>> ansSegment : Segments) {
                            if ((Objects.equals(ansSegment.get(0).get(0),minN1) && Objects.equals(ansSegment.get(0).get(1), minE1) && Objects.equals(ansSegment.get(1).get(0), minN2) && Objects.equals(ansSegment.get(1).get(1), minE2)) ||
                                    (Objects.equals(ansSegment.get(0).get(0), minN2) && Objects.equals(ansSegment.get(0).get(1), minE2) && Objects.equals(ansSegment.get(1).get(0), minN1) && Objects.equals(ansSegment.get(1).get(1), minE1))) {
                                exist = true;
                                break;
                            }
                        }
                    }
                    if (!exist) {
                        System.out.println(minDist);
                        System.out.println(minN1 + " " + minE1 + " " + minN2 + " " + minE2);
                        List<List<Double>> segment = new ArrayList<>();
                        List<Double> point1 = new ArrayList<>();
                        point1.add(minN1);
                        point1.add(minE1);
                        List<Double> point2 = new ArrayList<>();
                        point2.add(minN2);
                        point2.add(minE2);
                        segment.add(point1);
                        segment.add(point2);
                        Segments.add(segment);
                    }
                }
            }
        }

        return Segments;
    }
}
