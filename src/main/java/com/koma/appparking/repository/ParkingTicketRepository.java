package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingTicket;
import com.koma.appparking.domain.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    @Query("select pt from ParkingTicket pt " +
            "where pt.id = :id")
    ParkingTicket findByTicketId(Long id);

    //List<ParkingTicket> findByVehicleRegistrationNumber(String registrationNumber);   napisać custom query

    List<ParkingTicket> findByStatus(TicketStatus status);

    List<ParkingTicket> findByStatusAndArrivalTimeAfter(TicketStatus status, LocalDateTime arrivalTime);

//    Optional<Vehicle> findByIdAndVehicle(Long id, String registrationNumber); napisać custom query

    List<ParkingTicket> findByArrivalTimeBetween(LocalDateTime arrivalTime, LocalDateTime arrivalTime2);

    List<ParkingTicket> findByArrivalTimeBeforeAndDepartureTimeAfter(LocalDateTime arrivalTime, LocalDateTime departureTime);

}
