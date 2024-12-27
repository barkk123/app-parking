package com.koma.appparking.api;

// Importowanie klas i adnotacji potrzebnych do utworzenia kontrolera REST.
import com.koma.appparking.domain.ParkingTicket; // Klasa reprezentująca bilet parkingowy.
import com.koma.appparking.domain.TicketStatus; // Enum reprezentujący status biletu parkingowego.
import com.koma.appparking.services.ParkingTicketService; // Serwis zarządzający biletami parkingowymi.
import org.springframework.http.ResponseEntity; // Klasa umożliwiająca tworzenie odpowiedzi HTTP.
import org.springframework.web.bind.annotation.*; // Adnotacje REST API.

import java.util.List; // Import listy dla operacji na kolekcjach.

@RestController // Adnotacja oznaczająca, że klasa jest kontrolerem REST.
@RequestMapping("/api/parking-tickets") // Ustawienie głównego endpointu dla biletów parkingowych.
class ParkingTicketController { // Definicja kontrolera zarządzającego biletami parkingowymi.

    private final ParkingTicketService parkingTicketService;
    // Serwis, który będzie używany do wykonywania logiki biznesowej.

     ParkingTicketController(ParkingTicketService parkingTicketService) {
        // Konstruktor wstrzykujący serwis do kontrolera.
        this.parkingTicketService = parkingTicketService;
    }

    @GetMapping // Adnotacja oznaczająca, że metoda obsługuje żądania GET.
     ResponseEntity<List<ParkingTicket>> getAllTickets() {
        // Metoda pobierająca listę wszystkich biletów parkingowych.
        return ResponseEntity.ok(parkingTicketService.getAllTickets());
        // Zwraca odpowiedź HTTP 200 (OK) z listą biletów.
    }

    @GetMapping("/{id}") // Obsługuje żądania GET dla konkretnego biletu na podstawie identyfikatora.
     ResponseEntity<ParkingTicket> getTicketById(@PathVariable Long id) {
        // Metoda pobierająca bilet o podanym identyfikatorze.
        return parkingTicketService.getTicketById(id)
                // Wywołuje metodę serwisową, aby znaleźć bilet.
                .map(ResponseEntity::ok)
                // Jeśli bilet istnieje, zwraca HTTP 200 (OK) z biletem.
                .orElse(ResponseEntity.notFound().build());
        // Jeśli nie istnieje, zwraca HTTP 404 (Not Found).
    }

    @PostMapping // Adnotacja oznaczająca, że metoda obsługuje żądania POST.
     ResponseEntity<ParkingTicket> createTicket(@RequestBody ParkingTicket ticket) {
        // Metoda tworząca nowy bilet parkingowy.
        return ResponseEntity.ok(parkingTicketService.createTicket(ticket));
        // Zwraca odpowiedź HTTP 200 (OK) z nowo utworzonym biletem.
    }

    @PutMapping("/{id}") // Obsługuje żądania PUT dla aktualizacji biletu na podstawie identyfikatora.
     ResponseEntity<ParkingTicket> updateTicket(@PathVariable Long id, @RequestBody ParkingTicket updatedTicket) {
        // Metoda aktualizująca dane istniejącego biletu.
        return ResponseEntity.ok(parkingTicketService.updateTicket(id, updatedTicket));
        // Zwraca odpowiedź HTTP 200 (OK) z zaktualizowanym biletem.
    }

    @DeleteMapping("/{id}") // Obsługuje żądania DELETE dla usunięcia biletu na podstawie identyfikatora.
     ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        // Metoda usuwająca bilet parkingowy.
        parkingTicketService.deleteTicket(id);
        // Wywołuje metodę serwisową, aby usunąć bilet.
        return ResponseEntity.noContent().build();
        // Zwraca odpowiedź HTTP 204 (No Content) bez treści.
    }

    @PatchMapping("/{id}/status") // Obsługuje żądania PATCH dla zmiany statusu biletu.
     ResponseEntity<ParkingTicket> updateTicketStatus(@PathVariable Long id, @RequestParam TicketStatus status) {
        // Metoda aktualizująca status biletu.
        return ResponseEntity.ok(parkingTicketService.updateTicketStatus(id, status));
        // Zwraca odpowiedź HTTP 200 (OK) z zaktualizowanym biletem.
    }
}
