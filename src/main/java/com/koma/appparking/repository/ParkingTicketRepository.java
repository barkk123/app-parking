package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    // Możesz dodać własne zapytania, np. wyszukiwanie po statusie lub numerze rejestracyjnym pojazdu
    // List<ParkingTicket> findByStatus(TicketStatus status);
    // List<ParkingTicket> findByVehicleLicenseNumber(String licenseNumber);
}
