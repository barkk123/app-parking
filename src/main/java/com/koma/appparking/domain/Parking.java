package com.koma.appparking.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@NoArgsConstructor
@Table(name = "parking")
@Getter
@Setter
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer numberOfParkingSpots;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar")
    private ParkingType type;

    @NotNull
    private Boolean secured;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private ParkingLocation location;

    @OneToMany(
            mappedBy = "parking",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<ParkingSpot> parkingSpots = new ArrayList<>();

    public void addParkingSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
        parkingSpot.setParking(this);
    }

    public void removeParkingSpot(ParkingSpot parkingSpot) {
        parkingSpots.remove(parkingSpot);
        parkingSpot.setParking(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var parking = (Parking) o;
        return Objects.equals(id, parking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}