package com.koma.appparking.domain;

public enum ParkingType {
    OPEN_AIR("Parking na świeżym powietrzu"),
    COVERED("Parking pod zadaszeniem"),
    INDOOR("Parking wewnętrzny");

    private final String description;

    ParkingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
