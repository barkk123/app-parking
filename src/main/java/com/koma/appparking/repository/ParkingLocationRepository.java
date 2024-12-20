package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLocationRepository extends JpaRepository<ParkingLocation, Long> {
    // Możesz dodać tutaj metody wyszukiwania, jeśli są potrzebne
    // Na przykład:
    // List<ParkingLocation> findByCity(String city);
}
