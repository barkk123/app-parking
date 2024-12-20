package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    // Możesz dodać własne zapytania, np. wyszukiwanie według statusu
    // List<ParkingSpot> findByStatus(ParkingSpotStatus status);
}
