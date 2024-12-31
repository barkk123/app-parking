package com.koma.appparking.services;

import com.koma.appparking.api.ParkingTicketCreateModel;
import com.koma.appparking.domain.ParkingTicket;
import com.koma.appparking.domain.TicketStatus;
import com.koma.appparking.repository.ParkingTicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ParkingTicketService {
    private static final Logger logger = LogManager.getLogger(ParkingTicketService.class);
    private final ParkingTicketRepository parkingTicketRepository;


    public ParkingTicketService(ParkingTicketRepository parkingTicketRepository) {
        this.parkingTicketRepository = parkingTicketRepository;
    }

    @Transactional(readOnly = true)
    public List<ParkingTicket> getAllTickets() {
        logger.info("Fetching all parking tickets");
        return parkingTicketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ParkingTicket> getTicketById(Long id) {
        logger.debug("Fetching parking ticket with ID: {}", id);
        return parkingTicketRepository.findById(id);
    }

    @Transactional
    public void deleteTicket(Long id) {
        logger.warn("Deleting parking ticket with ID: {}", id);
        parkingTicketRepository.deleteById(id);
    }


    @Transactional
    public ParkingTicket createParkingTicket(ParkingTicketCreateModel ticketModel) {
        ParkingTicket newTicket = new ParkingTicket();
//pobrać z bazy danych dowolne wolne mijesce parkingowe i przypisać tu niżej z parkingspotRepostiory
        // newTicket.setParkingSpot(ticketModel.parkingSpotId());
//        newTicket.setVehicle(ticketModel.vehicle()); utworzyć w bazie pojazd jeśli jest to go pobrać z vehicle repostiory i przypisać do tego pojazdu
        newTicket.setArrivalTime(LocalDateTime.now());
        newTicket.setStatus(TicketStatus.ACTIVE);
      //  newTicket.setId();
        return parkingTicketRepository.save(newTicket);
    }

    @Transactional
    public ParkingTicket updateTicket(Long ticketId, ParkingTicket updatedTicket) {
        ParkingTicket ticket = parkingTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + ticketId));

        ticket.setParkingSpot(updatedTicket.getParkingSpot());
//        ticket.setVehicleRegistrationNumber(updatedTicket.getVehicleRegistrationNumber());
        ticket.setArrivalTime(updatedTicket.getArrivalTime());

        return parkingTicketRepository.save(ticket);
    }

}