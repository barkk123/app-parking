package com.koma.appparking.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name = "parking_spot")
@Getter
@Setter
public class ParkingSpot {

    @Id
    private Long id;

    private Integer number;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

}

