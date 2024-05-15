package ru.busoptimizer.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "points")
public class Points {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "n_latitude")
    private Double n_latitude;

    @Column(name = "e_longitude")
    private Double e_longitude;

    @OneToMany(mappedBy = "points")
    private List<BusesPoints> busesPoints = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "stops_id")
    private Stops stops;

}