package com.koma.appparking.api;

// Importowanie klas i adnotacji potrzebnych do utworzenia kontrolera REST.
import com.koma.appparking.domain.ParkingSpot; // Klasa reprezentująca miejsce parkingowe.
import com.koma.appparking.domain.ParkingSpotStatus; // Enum reprezentujący status miejsca parkingowego.
import com.koma.appparking.services.ParkingSpotService; // Serwis zarządzający miejscami parkingowymi.
import org.springframework.http.ResponseEntity; // Klasa umożliwiająca tworzenie odpowiedzi HTTP.
import org.springframework.web.bind.annotation.*; // Adnotacje REST API.

import java.util.List; // Import listy dla operacji na kolekcjach.

@RestController // Adnotacja oznaczająca, że klasa jest kontrolerem REST.
@RequestMapping("/api/parking-spots") // Ustawienie głównego endpointu dla miejsc parkingowych.
class ParkingSpotController { // Definicja kontrolera zarządzającego miejscami parkingowymi.

    private final ParkingSpotService parkingSpotService;
    // Serwis, który będzie używany do wykonywania logiki biznesowej.

    ParkingSpotController(ParkingSpotService parkingSpotService) {
        // Konstruktor wstrzykujący serwis do kontrolera.
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping // Adnotacja oznaczająca, że metoda obsługuje żądania GET.
    ResponseEntity<List<ParkingSpot>> getAllSpots() {
        // Metoda pobierająca listę wszystkich miejsc parkingowych.
        return ResponseEntity.ok(parkingSpotService.getAllSpots());
        // Zwraca odpowiedź HTTP 200 (OK) z listą miejsc.
    }

    @GetMapping("/{id}") // Obsługuje żądania GET dla konkretnego miejsca na podstawie identyfikatora.
    ResponseEntity<ParkingSpot> getSpotById(@PathVariable Long id) {
        // Metoda pobierająca miejsce o podanym identyfikatorze.
        return parkingSpotService.getSpotById(id)
                // Wywołuje metodę serwisową, aby znaleźć miejsce.
                .map(ResponseEntity::ok)
                // Jeśli miejsce istnieje, zwraca HTTP 200 (OK) z miejscem.
                .orElse(ResponseEntity.notFound().build());
        // Jeśli nie istnieje, zwraca HTTP 404 (Not Found).
    }

    @PostMapping // Adnotacja oznaczająca, że metoda obsługuje żądania POST.
    ResponseEntity<ParkingSpot> createSpot(@RequestBody ParkingSpot spot) {
        // Metoda tworząca nowe miejsce parkingowe.
        return ResponseEntity.ok(parkingSpotService.createSpot(spot));
        // Zwraca odpowiedź HTTP 200 (OK) z nowo utworzonym miejscem.
    }

    @PutMapping("/{id}") // Obsługuje żądania PUT dla aktualizacji miejsca na podstawie identyfikatora.
    ResponseEntity<ParkingSpot> updateSpot(@PathVariable Long id, @RequestBody ParkingSpot spot) {
        // Metoda aktualizująca dane istniejącego miejsca.
        return ResponseEntity.ok(parkingSpotService.updateSpot(id, spot));
        // Zwraca odpowiedź HTTP 200 (OK) z zaktualizowanym miejscem.
    }

    @DeleteMapping("/{id}") // Obsługuje żądania DELETE dla usunięcia miejsca na podstawie identyfikatora.
    ResponseEntity<Void> deleteSpot(@PathVariable Long id) {
        // Metoda usuwająca miejsce parkingowe.
        parkingSpotService.deleteSpot(id);
        // Wywołuje metodę serwisową, aby usunąć miejsce.
        return ResponseEntity.noContent().build();
        // Zwraca odpowiedź HTTP 204 (No Content) bez treści.
    }

    @PatchMapping("/{id}/status") // Obsługuje żądania PATCH dla zmiany statusu miejsca parkingowego.
    ResponseEntity<ParkingSpot> updateSpotStatus(@PathVariable Long id, @RequestParam ParkingSpotStatus status) {
        // Metoda aktualizująca status miejsca.
        return ResponseEntity.ok(parkingSpotService.updateSpotStatus(id, status));
        // Zwraca odpowiedź HTTP 200 (OK) z zaktualizowanym miejscem.
    }
}
