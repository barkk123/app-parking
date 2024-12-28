package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLocationRepository extends JpaRepository<ParkingLocation, Long> {

    @Query("SELECT pl FROM ParkingLocation pl " +
            "WHERE pl.street = :street AND pl.buildingNumber = :buildingNumber")
    ParkingLocation findByStreetAndBuildingNumber(String street, String buildingNumber);
}
