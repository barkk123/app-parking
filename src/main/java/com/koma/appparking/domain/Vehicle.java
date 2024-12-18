package com.koma.appparking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
public class Vehicle {

    @Id
    private String licenseNumber;

    private String mark;

    private String model;
// do dopisania equal i hashcode
}

