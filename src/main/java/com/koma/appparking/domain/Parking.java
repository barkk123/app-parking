package com.koma.appparking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(32)")
    private ParkingType type;

    private Boolean secured;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private ParkingLocation location;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private List<ParkingSpot> parkingSpots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parking parking = (Parking) o;
        return Objects.equals(id, parking.id) &&
                Objects.equals(name, parking.name) &&
                Objects.equals(numberOfParkingSpots, parking.numberOfParkingSpots) &&
                type == parking.type &&
                Objects.equals(location, parking.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}