package ru.busoptimizer.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "buses_stops")
public class BusesStops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buses_id")
    private Buses buses;

    @ManyToOne
    @JoinColumn(name = "stops_id")
    private Stops stops;

    @Column(name = "forward_direction")
    private Boolean forward_direction;

    @Column(name = "reverse_direction")
    private Boolean reverse_direction;

}