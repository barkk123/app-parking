package com.koma.appparking.api;

import com.koma.appparking.domain.Vehicle;
import com.koma.appparking.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking/vehicles")
class VehicleController {

    private final VehicleService vehicleService;

    VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Get all vehicles", description = "Retrieve a list of all registered vehicles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of vehicles",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    ResponseEntity<List<Vehicle>> getAll() {
        return ResponseEntity.ok(vehicleService.get());
    }

    @Operation(summary = "Get vehicle by license number", description = "Retrieve details of a vehicle using its license number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vehicle",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
    })
    @GetMapping("/{licenseNumber}")
    ResponseEntity<Vehicle> getAll(@PathVariable String licenseNumber) {
        return vehicleService.get(licenseNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new vehicle", description = "Register a new vehicle in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created vehicle",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    @PostMapping
    ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.create(vehicle));
    }

    @Operation(summary = "Update a vehicle", description = "Update the details of an existing vehicle by license number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated vehicle",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
    })
    @PutMapping("/{licenseNumber}")
    ResponseEntity<Vehicle> update(@PathVariable String licenseNumber, @RequestBody Vehicle updatedVehicle) {
        return ResponseEntity.ok(vehicleService.update(licenseNumber, updatedVehicle));
    }

    @Operation(summary = "Delete a vehicle", description = "Remove a vehicle from the system using its license number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted vehicle", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
    })
    @DeleteMapping("/{licenseNumber}")
    ResponseEntity<Void> delete(@PathVariable String licenseNumber) {
        vehicleService.delete(licenseNumber);
        return ResponseEntity.noContent().build();
    }
}
