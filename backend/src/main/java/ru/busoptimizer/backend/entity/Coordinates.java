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
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "n_latitude")
    private Double n_latitude;

    @Column(name = "e_longitude")
    private Double e_longitude;

    @ManyToOne
    @JoinColumn(name = "stops_id")
    private Stops stops;

}