package com.koma.appparking.api;

// Importowanie klas i adnotacji potrzebnych do utworzenia kontrolera REST.
import com.koma.appparking.domain.ParkingLocation; // Klasa reprezentująca lokalizację parkingową.
import com.koma.appparking.services.ParkingLocationService; // Serwis zarządzający lokalizacjami parkingowymi.
import org.springframework.http.ResponseEntity; // Klasa umożliwiająca tworzenie odpowiedzi HTTP.
import org.springframework.web.bind.annotation.*; // Adnotacje REST API.

import java.util.List; // Import listy dla operacji na kolekcjach.

@RestController // Adnotacja oznaczająca, że klasa jest kontrolerem REST.
@RequestMapping("/api/parking-locations") // Ustawienie głównego endpointu dla lokalizacji parkingowych.
class ParkingLocationController { // Definicja kontrolera zarządzającego lokalizacjami parkingowymi.

    private final ParkingLocationService parkingLocationService;
    // Serwis, który będzie używany do wykonywania logiki biznesowej.

    ParkingLocationController(ParkingLocationService parkingLocationService) {
        // Konstruktor wstrzykujący serwis do kontrolera.
        this.parkingLocationService = parkingLocationService;
    }

    @GetMapping // Adnotacja oznaczająca, że metoda obsługuje żądania GET.
    ResponseEntity<List<ParkingLocation>> getAllLocations() {
        // Metoda pobierająca listę wszystkich lokalizacji parkingowych.
        return ResponseEntity.ok(parkingLocationService.getAllLocations());
        // Zwraca odpowiedź HTTP 200 (OK) z listą lokalizacji.
    }

    @GetMapping("/{id}") // Obsługuje żądania GET dla konkretnej lokalizacji na podstawie identyfikatora.
    ResponseEntity<ParkingLocation> getLocationById(@PathVariable Long id) {
        // Metoda pobierająca lokalizację o podanym identyfikatorze.
        return parkingLocationService.getLocationById(id)
                // Wywołuje metodę serwisową, aby znaleźć lokalizację.
                .map(ResponseEntity::ok)
                // Jeśli lokalizacja istnieje, zwraca HTTP 200 (OK) z lokalizacją.
                .orElse(ResponseEntity.notFound().build());
        // Jeśli nie istnieje, zwraca HTTP 404 (Not Found).
    }

    @PostMapping // Adnotacja oznaczająca, że metoda obsługuje żądania POST.
    ResponseEntity<ParkingLocation> createLocation(@RequestBody ParkingLocation location) {
        // Metoda tworząca nową lokalizację parkingową.
        return ResponseEntity.ok(parkingLocationService.createLocation(location));
        // Zwraca odpowiedź HTTP 200 (OK) z nowo utworzoną lokalizacją.
    }

    @PutMapping("/{id}") // Obsługuje żądania PUT dla aktualizacji lokalizacji na podstawie identyfikatora.
    ResponseEntity<ParkingLocation> updateLocation(@PathVariable Long id, @RequestBody ParkingLocation location) {
        // Metoda aktualizująca dane istniejącej lokalizacji.
        return ResponseEntity.ok(parkingLocationService.updateLocation(id, location));
        // Zwraca odpowiedź HTTP 200 (OK) z zaktualizowaną lokalizacją.
    }

    @DeleteMapping("/{id}") // Obsługuje żądania DELETE dla usunięcia lokalizacji na podstawie identyfikatora.
    ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        // Metoda usuwająca lokalizację parkingową.
        parkingLocationService.deleteLocation(id);
        // Wywołuje metodę serwisową, aby usunąć lokalizację.
        return ResponseEntity.noContent().build();
        // Zwraca odpowiedź HTTP 204 (No Content) bez treści.
    }
}
