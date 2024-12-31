package com.koma.appparking.api;


import com.koma.appparking.domain.ParkingLocation;
import com.koma.appparking.services.ParkingLocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking/parking-locations")
class ParkingLocationController {

    @NotNull
    private final ParkingLocationService parkingLocationService;


    ParkingLocationController(ParkingLocationService parkingLocationService) {
        this.parkingLocationService = parkingLocationService;
    }

    @GetMapping
    ResponseEntity<List<ParkingLocation>> getAllLocations() {
        return ResponseEntity.ok(parkingLocationService.getAllLocations());
    }

    @GetMapping("/{id}")
    ResponseEntity<ParkingLocation> getLocationById(@PathVariable Long id) {
        return parkingLocationService.getLocationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<ParkingLocation> createLocation(@RequestBody ParkingLocation location) {
        return ResponseEntity.ok(parkingLocationService.createLocation(location));
    }

    @PutMapping("/{id}")
    ResponseEntity<ParkingLocation> updateLocation(@PathVariable Long id, @RequestBody ParkingLocation location) {
        return ResponseEntity.ok(parkingLocationService.update(id, location));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        parkingLocationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
