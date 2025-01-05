package com.koma.appparking.services;

import com.koma.appparking.api.ParkingTicketCreateModel;
import com.koma.appparking.domain.*;
import com.koma.appparking.repository.ParkingSpotRepository;
import com.koma.appparking.repository.ParkingTicketRepository;
import com.koma.appparking.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ParkingTicketService {
    private static final Logger logger = LogManager.getLogger(ParkingTicketService.class);
    private final ParkingTicketRepository parkingTicketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingTicketService(ParkingSpotRepository parkingSpotRepository,
                                VehicleRepository vehicleRepository,
                                ParkingTicketRepository parkingTicketRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingTicketRepository = parkingTicketRepository;
    }

    @Transactional(readOnly = true)
    public List<ParkingTicket> get() {
        logger.info("Fetching all parking tickets");
        return parkingTicketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ParkingTicket> get(Long id) {
        logger.debug("Fetching parking ticket with ID: {}", id);
        return parkingTicketRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        parkingTicketRepository.deleteById(id);
        logger.info("Successfully deleted parking ticket by ID: {}", id);
    }

    @Transactional
    public ParkingTicket createParkingTicket(ParkingTicketCreateModel ticketModel) {

        var vehicle = vehicleRepository.findByLicenseNumber(ticketModel.vehicleRegistrationNumber())
                .orElseThrow(() -> new EntityNotFoundException("Pojazd o id nie istnieje: " + ticketModel.vehicleRegistrationNumber()));

        List<ParkingTicket> activeTickets = parkingTicketRepository.findByVehicleAndStatus(vehicle, TicketStatus.ACTIVE);
        if (!activeTickets.isEmpty()) {
            throw new IllegalStateException("Pojazd już zajmuje miejsce parkingowe.");
        }

        var freeSpot = parkingSpotRepository.findFirstByStatus(ParkingSpotStatus.FREE)
                .orElseThrow(() -> new NoSuchElementException("Brak dostępnych wolnych miejsc parkingowych"));

        var newTicket = ParkingTicket.builder().vehicle(vehicle)
                .parkingSpot(freeSpot)
                .status(TicketStatus.ACTIVE)
                .arrivalTime(LocalDateTime.now())
                .build();

        freeSpot.setStatus(ParkingSpotStatus.OCCUPIED);

        logger.info("Successfully created parking ticket");
        return parkingTicketRepository.save(newTicket);
    }

    @Transactional
    public double payForParkingTicket(String licenseNumber, Long parkingSpotId) {
        var ticket = parkingTicketRepository
                .findByVehicle_LicenseNumberAndParkingSpot_IdAndStatus(licenseNumber, parkingSpotId, TicketStatus.ACTIVE)
                .orElseThrow(() -> new NoSuchElementException("Brak aktywnego biletu dla podanego pojazdu i miejsca parkingowego"));

        LocalDateTime now = LocalDateTime.now();
        long hours = Duration.between(ticket.getArrivalTime(), now).toHours();
        double ratePerHour = 5.0;
        double totalCharge = hours * ratePerHour;

        ticket.setStatus(TicketStatus.PAID);
        ticket.setDepartureTime(now);
        parkingTicketRepository.save(ticket);

        ParkingSpot parkingSpot = ticket.getParkingSpot();
        parkingSpot.setStatus(ParkingSpotStatus.FREE);
        parkingSpotRepository.save(parkingSpot);

        logger.info("Bilet opłacony. Kwota: {}, Rejestracja: {}, Miejsce: {}", totalCharge, licenseNumber, parkingSpotId);
        return totalCharge;
    }


}