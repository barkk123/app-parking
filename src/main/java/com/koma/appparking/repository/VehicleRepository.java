package com.koma.appparking.repository;

import com.koma.appparking.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    @Query("SELECT v FROM Vehicle v " +
            "WHERE v.licenseNumber = :licenseNumber")
    Vehicle findByLicenseNumber(String licenseNumber);
}
