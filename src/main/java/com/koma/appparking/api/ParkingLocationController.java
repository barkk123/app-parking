package com.koma.appparking.api;

import com.koma.appparking.domain.ParkingLocation;
import com.koma.appparking.services.ParkingLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking/parking-locations")
class ParkingLocationController {

    private final ParkingLocationService parkingLocationService;

    ParkingLocationController(ParkingLocationService parkingLocationService) {
        this.parkingLocationService = parkingLocationService;
    }

    @Operation(summary = "Get all parking locations", description = "Retrieves a list of all parking locations.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of parking locations")
    })
    @GetMapping
    ResponseEntity<List<ParkingLocation>> getAll() {
        return ResponseEntity.ok(parkingLocationService.get());
    }

    @Operation(summary = "Get a parking location by ID", description = "Retrieves details of a specific parking location by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the parking location"),
            @ApiResponse(responseCode = "404", description = "Parking location not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<ParkingLocation> getAll(
            @Parameter(description = "ID of the parking location", required = true, example = "1")
            @PathVariable Long id) {
        return parkingLocationService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new parking location", description = "Creates a new parking location with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created the parking location")
    })
    @PostMapping
    ResponseEntity<ParkingLocation> create(
            @Parameter(description = "Details of the parking location to be created", required = true)
           @Valid @RequestBody ParkingLocation location) {
        return ResponseEntity.ok(parkingLocationService.create(location));
    }

    @Operation(summary = "Update a parking location", description = "Updates the details of an existing parking location.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the parking location"),
            @ApiResponse(responseCode = "404", description = "Parking location not found")
    })
    @PutMapping("/{id}")
    ResponseEntity<ParkingLocation> update(
            @Parameter(description = "ID of the parking location to be updated", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated details of the parking location", required = true)
            @RequestBody ParkingLocation location) {
        return ResponseEntity.ok(parkingLocationService.update(id, location));
    }

    @Operation(summary = "Delete a parking location", description = "Deletes a parking location by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted the parking location"),
            @ApiResponse(responseCode = "404", description = "Parking location not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID of the parking location to be deleted", required = true, example = "1")
            @PathVariable Long id) {
        parkingLocationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
