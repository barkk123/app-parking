package com.koma.appparking.api;

import com.koma.appparking.domain.Vehicle;
import com.koma.appparking.services.VehicleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking/vehicles")
class VehicleController {

    @NotNull
    private final VehicleService vehicleService;


    VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.get());
    }

    @GetMapping("/{licenseNumber}")
    ResponseEntity<Vehicle> getVehicleByLicenseNumber(@PathVariable String licenseNumber) {
        return vehicleService.get(licenseNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.create(vehicle));
    }

    @PutMapping("/{licenseNumber}")
    ResponseEntity<Vehicle> updateVehicle(@PathVariable String licenseNumber, @RequestBody Vehicle updatedVehicle) {
        return ResponseEntity.ok(vehicleService.update(licenseNumber, updatedVehicle));
    }

    @DeleteMapping("/{licenseNumber}")
    ResponseEntity<Void> deleteVehicle(@PathVariable String licenseNumber) {
        vehicleService.delete(licenseNumber);
        return ResponseEntity.noContent().build();
    }
}
