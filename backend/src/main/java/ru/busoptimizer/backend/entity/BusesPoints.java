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
@Table(name = "buses_points")
public class BusesPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buses_id")
    private Buses buses;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private Points points;

    @Column(name = "direction")
    private Boolean direction;

}