package com.koma.appparking.repository;

import com.koma.appparking.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    // Możesz dodać własne zapytania, np. wyszukiwanie po marce lub modelu
    // List<Vehicle> findByMark(String mark);
    // List<Vehicle> findByModel(String model);
}
