package com.koma.appparking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "parking")
@Getter
@Setter
public class Parking {

    @Id
    private Long id;

    private String name;

    private Integer numberOfParkingSpots;

    private ParkingType type;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private ParkingLocation location;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private List<ParkingSpot> parkingSpots;


}