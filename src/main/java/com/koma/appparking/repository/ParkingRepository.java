package com.koma.appparking.repository;

import com.koma.appparking.domain.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Query("SELECT COUNT(ps) " +
            "FROM ParkingSpot ps " +
            "LEFT JOIN ps.parking p " +
            "WHERE p.name = :name AND ps.status = 'FREE'")
    Integer findFreeSpotsByParkingName(@Param("name") String name);

}
