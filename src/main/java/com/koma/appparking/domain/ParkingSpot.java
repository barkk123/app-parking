package com.koma.appparking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "parking_spot")
@Getter
@Setter
public class ParkingSpot {
    @Id
    private Long id;

    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar")
    private ParkingSpotStatus status;

    @ManyToOne
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private Parking parking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(number, that.number) &&
                Objects.equals(status, that.status) &&
                Objects.equals(parking, that.parking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
