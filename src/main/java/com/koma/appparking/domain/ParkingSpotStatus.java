package com.koma.appparking.domain;

public enum ParkingSpotStatus {
    FREE("Wolne"),
    OCCUPIED("Zajęte");

    private final String statusName;

    ParkingSpotStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
