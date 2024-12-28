package com.koma.appparking.services;

// Importowanie klas i interfejsów potrzebnych do działania serwisu ParkingTicketService.

import com.koma.appparking.domain.ParkingTicket;
import com.koma.appparking.domain.TicketStatus;
import com.koma.appparking.repository.ParkingTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Service
@Transactional
public class ParkingTicketService { // Definicja serwisu obsługującego bilety parkingowe.
    private static final Logger logger = LogManager.getLogger(ParkingTicketService.class);
    private final ParkingTicketRepository parkingTicketRepository;
    // Deklaracja repozytorium, które będzie używane do operacji na bazie danych.

    public ParkingTicketService(ParkingTicketRepository parkingTicketRepository) {
        // Konstruktor wstrzykujący repozytorium przy tworzeniu obiektu serwisu.
        this.parkingTicketRepository = parkingTicketRepository;
    }

    public List<ParkingTicket> getAllTickets() {
        logger.info("Fetching all parking tickets");
        // Metoda pobierająca listę wszystkich biletów z bazy danych.
        return parkingTicketRepository.findAll();
        // Wywołanie metody findAll z repozytorium, która zwraca wszystkie rekordy.
    }

    public Optional<ParkingTicket> getTicketById(Long id) {
        logger.debug("Fetching parking ticket with ID: {}", id);
        // Metoda pobierająca bilet o konkretnym identyfikatorze.
        return parkingTicketRepository.findById(id);
        // Wywołanie metody findById, która zwraca Optional z biletem lub pusty wynik.
    }

    public ParkingTicket createTicket(ParkingTicket ticket) {
        logger.info("Creating a new parking ticket: {}", ticket);
        // Metoda tworząca nowy bilet parkingowy.
        return parkingTicketRepository.save(ticket);
        // Zapisuje nowy bilet w bazie danych i zwraca zapisany obiekt.
    }

    public ParkingTicket updateTicket(Long id, ParkingTicket updatedTicket) {
        logger.debug("Updating parking ticket with ID: {}", id);
        logger.debug("Updating ticket details: {}", updatedTicket);
        // Metoda aktualizująca dane istniejącego biletu.
        return parkingTicketRepository.findById(id)
                // Szuka biletu o podanym identyfikatorze.
                .map(ticket -> {
                    // Jeśli bilet istnieje, aktualizuje jego pola.
                    ticket.setParkingSpot(updatedTicket.getParkingSpot());
                    // Ustawia zaktualizowany numer miejsca parkingowego.
                    ticket.setVehicle(updatedTicket.getVehicle());
                    // Ustawia zaktualizowany pojazd.
                    ticket.setStatus(updatedTicket.getStatus());
                    // Ustawia zaktualizowany status biletu.
                    ticket.setArrivalTime(updatedTicket.getArrivalTime());
                    // Ustawia zaktualizowany czas przyjazdu.
                    ticket.setDepartureTime(updatedTicket.getDepartureTime());
                    // Ustawia zaktualizowany czas wyjazdu.
                    ticket.setFee(updatedTicket.getFee());
                    // Ustawia zaktualizowaną opłatę za parking.
                    return parkingTicketRepository.save(ticket);
                    // Zapisuje zaktualizowany bilet w bazie danych.
                })
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
        // Jeśli bilet o podanym identyfikatorze nie istnieje, rzuca wyjątek.
    }

    public void deleteTicket(Long id) {
        logger.warn("Deleting parking ticket with ID: {}", id);
        // Metoda usuwająca bilet o podanym identyfikatorze.
        parkingTicketRepository.deleteById(id);
        // Wywołanie metody deleteById z repozytorium, która usuwa bilet z bazy danych.
    }

    public ParkingTicket updateTicketStatus(Long id, TicketStatus status) {
        logger.info("Updating status of ticket with ID: {} to {}", id, status);
        // Metoda aktualizująca tylko status biletu.
        return parkingTicketRepository.findById(id)
                // Szuka biletu o podanym identyfikatorze.
                .map(ticket -> {
                    // Jeśli bilet istnieje, aktualizuje jego status.
                    ticket.setStatus(status);
                    // Ustawia nowy status biletu.
                    return parkingTicketRepository.save(ticket);
                    // Zapisuje zaktualizowany bilet w bazie danych.
                })
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
        // Jeśli bilet o podanym identyfikatorze nie istnieje, rzuca wyjątek.
    }
}