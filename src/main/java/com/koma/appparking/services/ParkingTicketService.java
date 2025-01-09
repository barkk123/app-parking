package com.koma.appparking.services;

import com.koma.appparking.api.ParkingTicketCreateModel;
import com.koma.appparking.api.ParkingTicketPayModel;
import com.koma.appparking.api.ParkingTicketSummary;
import com.koma.appparking.domain.ParkingSpotStatus;
import com.koma.appparking.domain.ParkingTicket;
import com.koma.appparking.domain.TicketStatus;
import com.koma.appparking.repository.ParkingSpotRepository;
import com.koma.appparking.repository.ParkingTicketRepository;
import com.koma.appparking.repository.VehicleRepository;
import com.koma.appparking.services.common.FeeFormatter;
import com.koma.appparking.services.common.ParkingTimeFormatter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ParkingTicketService {
    private final ParkingTicketRepository parkingTicketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingTicketFeeService parkingTicketFeeService;

    @Transactional(readOnly = true)
    public List<ParkingTicket> getAll() {
        log.info("Fetching all parking tickets");
        return parkingTicketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ParkingTicket> get(Long id) {
        log.info("Fetching parking ticket with ID: {}", id);
        return parkingTicketRepository.findById(id);
    }

    public void delete(Long id) {
        parkingTicketRepository.deleteById(id);
        log.info("Successfully deleted parking ticket by ID: {}", id);
    }

    public ParkingTicket create(ParkingTicketCreateModel ticketModel) {

        var vehicle = vehicleRepository.findByLicenseNumber(ticketModel.vehicleRegistrationNumber())
                .orElseThrow(() -> new EntityNotFoundException("A vehicle with the given ID does not exist: " + ticketModel.vehicleRegistrationNumber()));

        List<ParkingTicket> activeTickets = parkingTicketRepository.findByVehicleAndStatus(vehicle, TicketStatus.ACTIVE);
        if (!activeTickets.isEmpty()) {
            throw new IllegalStateException("The vehicle is already occupying a parking spot.");
        }

        var freeSpot = parkingSpotRepository.findFirstByStatus(ParkingSpotStatus.FREE)
                .orElseThrow(() -> new NoSuchElementException("No available free parking spots."));

        var newTicket = ParkingTicket.builder().vehicle(vehicle)
                .parkingSpot(freeSpot)
                .status(TicketStatus.ACTIVE)
                .arrivalTime(LocalDateTime.now())
                .build();

        freeSpot.setStatus(ParkingSpotStatus.OCCUPIED);

        log.info("Successfully created parking ticket");
        return parkingTicketRepository.save(newTicket);
    }

    public ParkingTicketSummary pay(ParkingTicketPayModel model) {
        var ticket = parkingTicketRepository
                .findByVehicleLicenseNumberAndStatus(model.vehicleLicenseNumber(), TicketStatus.ACTIVE)
                .orElseThrow(() -> new NoSuchElementException("No active ticket for the given vehicle and parking spot."));

        ticket.setDepartureTime(LocalDateTime.now());
        var totalFee = parkingTicketFeeService.calculateTotalFee(ticket);

        ticket.setFee(totalFee);
        ticket.setStatus(TicketStatus.PAID);
        var parkingSpot = ticket.getParkingSpot();
        parkingSpot.setStatus(ParkingSpotStatus.FREE);

        log.info("Successfully paid ticket for vehicle: {}", ticket.getVehicle().getLicenseNumber());

        return new ParkingTicketSummary(FeeFormatter.format(ticket.getFee()),
                ParkingTimeFormatter.format(ticket.getArrivalTime(), ticket.getDepartureTime()));
    }//logi na serwisach

}