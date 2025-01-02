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
        logger.warn("Deleting parking ticket with ID: {}", id);
        parkingTicketRepository.deleteById(id);
    }


    @Transactional
    public ParkingTicket createParkingTicket(ParkingTicketCreateModel ticketModel) {
        var newTicket = new ParkingTicket();

        var freeSpot = parkingSpotRepository.findFirstByStatus(ParkingSpotStatus.FREE)
                .orElseThrow(() -> new NoSuchElementException("Brak dostępnych wolnych miejsc parkingowych"));

       var vehicle = vehicleRepository.findByLicenseNumber(ticketModel.vehicleRegistrationNumber())
               .orElseThrow(()-> new EntityNotFoundException("Pojazd o id nie istnieje: " + ticketModel.vehicleRegistrationNumber()));

        newTicket.setVehicle(vehicle);
        newTicket.setParkingSpot(freeSpot);

        newTicket.setStatus(TicketStatus.ACTIVE);
        newTicket.setArrivalTime(LocalDateTime.now());

        return parkingTicketRepository.save(newTicket);
    }
}
//dodać sobie kontroler advice