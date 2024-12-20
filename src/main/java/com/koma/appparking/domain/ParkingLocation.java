package com.koma.appparking.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "parking_location")
@Getter
@Setter
public class ParkingLocation {
    @Id
    private Long id;

    private String street;

    private String buildingNumber;

    private String city;

    private String zipCode;

    @OneToOne(mappedBy = "location")
    private Parking parking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLocation that = (ParkingLocation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(street, that.street) &&
                Objects.equals(buildingNumber, that.buildingNumber) &&
                Objects.equals(city, that.city) &&
                Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
