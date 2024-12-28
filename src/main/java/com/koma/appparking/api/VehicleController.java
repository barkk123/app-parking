package com.koma.appparking.api;

// Importowanie klas i adnotacji potrzebnych do utworzenia kontrolera REST.
import com.koma.appparking.domain.Vehicle; // Klasa reprezentująca pojazd.
import com.koma.appparking.services.VehicleService; // Serwis zarządzający pojazdami.
import org.springframework.http.ResponseEntity; // Klasa umożliwiająca tworzenie odpowiedzi HTTP.
import org.springframework.web.bind.annotation.*; // Adnotacje REST API.

import java.util.List; // Import listy dla operacji na kolekcjach.

@RestController // Adnotacja oznaczająca, że klasa jest kontrolerem REST.
@RequestMapping("/api/vehicles") // Ustawienie głównego endpointu dla pojazdów.
class VehicleController { // Definicja kontrolera zarządzającego pojazdami.

    private final VehicleService vehicleService;
    // Serwis, który będzie używany do wykonywania logiki biznesowej.

    VehicleController(VehicleService vehicleService) {
        // Konstruktor wstrzykujący serwis do kontrolera.
        this.vehicleService = vehicleService;
    }

    @GetMapping // Adnotacja oznaczająca, że metoda obsługuje żądania GET.
    ResponseEntity<List<Vehicle>> getAllVehicles() {
        // Metoda pobierająca listę wszystkich pojazdów.
        return ResponseEntity.ok(vehicleService.getAllVehicles());
        // Zwraca odpowiedź HTTP 200 (OK) z listą pojazdów.
    }

    @GetMapping("/{licenseNumber}") // Obsługuje żądania GET dla konkretnego pojazdu na podstawie numeru rejestracyjnego.
    ResponseEntity<Vehicle> getVehicleByLicenseNumber(@PathVariable String licenseNumber) {
        // Metoda pobierająca pojazd o podanym numerze rejestracyjnym.
        return vehicleService.getVehicleByLicenseNumber(licenseNumber)
                // Wywołuje metodę serwisową, aby znaleźć pojazd.
                .map(ResponseEntity::ok)
                // Jeśli pojazd istnieje, zwraca HTTP 200 (OK) z pojazdem.
                .orElse(ResponseEntity.notFound().build());
        // Jeśli nie istnieje, zwraca HTTP 404 (Not Found).
    }

    @PostMapping // Adnotacja oznaczająca, że metoda obsługuje żądania POST.
    ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        // Metoda tworząca nowy pojazd.
        return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
        // Zwraca odpowiedź HTTP 200 (OK) z nowo utworzonym pojazdem.
    }

    @PutMapping("/{licenseNumber}") // Obsługuje żądania PUT dla aktualizacji pojazdu na podstawie numeru rejestracyjnego.
    ResponseEntity<Vehicle> updateVehicle(@PathVariable String licenseNumber, @RequestBody Vehicle updatedVehicle) {
        // Metoda aktualizująca dane istniejącego pojazdu.
        return ResponseEntity.ok(vehicleService.updateVehicle(licenseNumber, updatedVehicle));
        // Zwraca odpowiedź HTTP 200 (OK) z zaktualizowanym pojazdem.
    }

    @DeleteMapping("/{licenseNumber}") // Obsługuje żądania DELETE dla usunięcia pojazdu na podstawie numeru rejestracyjnego.
    ResponseEntity<Void> deleteVehicle(@PathVariable String licenseNumber) {
        // Metoda usuwająca pojazd na podstawie numeru rejestracyjnego.
        vehicleService.deleteVehicle(licenseNumber);
        // Wywołuje metodę serwisową, aby usunąć pojazd.
        return ResponseEntity.noContent().build();
        // Zwraca odpowiedź HTTP 204 (No Content) bez treści.
    }
}
