package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingTicket;
import com.koma.appparking.domain.TicketStatus;
import com.koma.appparking.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    @Query("select pt from ParkingTicket pt " +
            "where pt.id = :id")
    ParkingTicket findByTicketId(Long id);

    @Query("select pt from ParkingTicket pt where pt.vehicle.licenseNumber = :licenseNumber")
    List<ParkingTicket> findByVehicleRegistrationNumber(@Param("licenseNumber") String licenseNumber);


    List<ParkingTicket> findByStatus(TicketStatus status);

    List<ParkingTicket> findByStatusAndArrivalTimeAfter(TicketStatus status, LocalDateTime arrivalTime);

    @Query("select pt.vehicle from ParkingTicket pt where pt.id = :id and pt.vehicle.licenseNumber = :licenseNumber")
    Optional<Vehicle> findByIdAndVehicle(@Param("id") Long id, @Param("licenseNumber") String registrationNumber);


    List<ParkingTicket> findByArrivalTimeBetween(LocalDateTime arrivalTime, LocalDateTime arrivalTime2);

    List<ParkingTicket> findByArrivalTimeBeforeAndDepartureTimeAfter(LocalDateTime arrivalTime, LocalDateTime departureTime);

}
