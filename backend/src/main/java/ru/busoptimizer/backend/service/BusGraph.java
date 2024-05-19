package ru.busoptimizer.backend.service;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.busoptimizer.backend.entity.BusesPoints;
import ru.busoptimizer.backend.repository.BusesPointsRepository;
import ru.busoptimizer.backend.repository.BusesRepository;
import ru.busoptimizer.backend.repository.StopsRepository;

import java.util.*;

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
        String startBusNames = "75";

        visited[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            Integer p = queue.poll();

            for(int i = 0; i < stopsRepository.count(); i++) {
                if (!visited[i] && busGraph.get(p).get(i).exist) {
                    visited[i] = true;
                    if (busGraph.get(p).get(i).busNames.contains(startBusNames)) {
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

    public void findFarStops() {
        for(int i = 0; i < stopsRepository.count(); i++) {
            List<Integer> farStops = bfs(i);

        }
    }
}
