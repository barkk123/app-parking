package com.koma.appparking.repository;

import com.koma.appparking.domain.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    // Możesz dodać tutaj dodatkowe metody wyszukiwania, jeśli są potrzebne
    // Na przykład:
    // List<Parking> findByName(String name);
}
