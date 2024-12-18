package com.koma.appparking.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name = "parking_location")
@Getter
@Setter
public class ParkingLocation {

    @Id
    private Long id;


    private String street;


    private String number;


    private String city;


    private String zipCode;

    // Relacja 1:1 z Parking
    @OneToOne(mappedBy = "location")
    private Parking parking;
    //do kazdej klasy napisać metody equals oraz hashCode
}
