package com.koma.appparking.api;


import com.koma.appparking.domain.ParkingSpot;
import com.koma.appparking.domain.ParkingSpotStatus;
import com.koma.appparking.services.ParkingSpotService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking/parking-spots")
class ParkingSpotController {

    @NotNull
    private final ParkingSpotService parkingSpotService;

    ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    ResponseEntity<List<ParkingSpot>> getAllSpots() {
        return ResponseEntity.ok(parkingSpotService.getAllSpots());
    }

    @GetMapping("/{id}")
    ResponseEntity<ParkingSpot> getSpotById(@PathVariable Long id) {
        return parkingSpotService.getSpotById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<ParkingSpot> createSpot(@RequestBody ParkingSpot spot) {
        return ResponseEntity.ok(parkingSpotService.createSpot(spot));
    }

    @PutMapping("/{id}")
    ResponseEntity<ParkingSpot> updateSpot(@PathVariable Long id, @RequestBody ParkingSpot spot) {
        return ResponseEntity.ok(parkingSpotService.updateSpot(id, spot));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSpot(@PathVariable Long id) {
        parkingSpotService.deleteSpot(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    ResponseEntity<ParkingSpot> updateSpotStatus(@PathVariable Long id, @RequestParam ParkingSpotStatus status) {
        return ResponseEntity.ok(parkingSpotService.updateSpotStatus(id, status));
    }
}
