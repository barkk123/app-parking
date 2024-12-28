package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    @Query("SELECT ps FROM ParkingSpot ps " +
            "WHERE ps.parking.id = :parkingId AND ps.status = 'FREE'")
    List<ParkingSpot> findFreeParkingSpotsByParkingId(Long parkingId);
}
