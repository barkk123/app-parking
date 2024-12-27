package com.koma.appparking.services;

// Importowanie klas i interfejsów potrzebnych do działania serwisu ParkingTicketService.
import com.koma.appparking.domain.ParkingTicket; // Reprezentuje encję biletu parkingowego.
import com.koma.appparking.repository.ParkingTicketRepository; // Interfejs repozytorium do operacji na bazie danych.
import com.koma.appparking.domain.TicketStatus; // Enum reprezentujący status biletu parkingowego.
import org.springframework.stereotype.Service; // Adnotacja oznaczająca, że klasa jest serwisem w kontekście Springa.

import java.util.List; // Do obsługi list wynikowych.
import java.util.Optional; // Do obsługi wartości opcjonalnych.

@Service
public class ParkingTicketService { // Definicja serwisu obsługującego bilety parkingowe.

    private final ParkingTicketRepository parkingTicketRepository;
    // Deklaracja repozytorium, które będzie używane do operacji na bazie danych.

    public ParkingTicketService(ParkingTicketRepository parkingTicketRepository) {
        // Konstruktor wstrzykujący repozytorium przy tworzeniu obiektu serwisu.
        this.parkingTicketRepository = parkingTicketRepository;
    }

    public List<ParkingTicket> getAllTickets() {
        // Metoda pobierająca listę wszystkich biletów z bazy danych.
        return parkingTicketRepository.findAll();
        // Wywołanie metody findAll z repozytorium, która zwraca wszystkie rekordy.
    }

    public Optional<ParkingTicket> getTicketById(Long id) {
        // Metoda pobierająca bilet o konkretnym identyfikatorze.
        return Optional.ofNullable(parkingTicketRepository.findByTicketId(id));
        // Wywołanie metody findById, która zwraca Optional z biletem lub pusty wynik.
    }

    public ParkingTicket createTicket(ParkingTicket ticket) {
        // Metoda tworząca nowy bilet parkingowy.
        return parkingTicketRepository.save(ticket);
        // Zapisuje nowy bilet w bazie danych i zwraca zapisany obiekt.
    }

    public ParkingTicket updateTicket(Long id, ParkingTicket updatedTicket) {
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
        // Metoda usuwająca bilet o podanym identyfikatorze.
        parkingTicketRepository.deleteById(id);
        // Wywołanie metody deleteById z repozytorium, która usuwa bilet z bazy danych.
    }

    public ParkingTicket updateTicketStatus(Long id, TicketStatus status) {
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
