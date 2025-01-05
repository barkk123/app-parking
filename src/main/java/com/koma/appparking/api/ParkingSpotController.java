package com.koma.appparking.api;


import com.koma.appparking.domain.ParkingSpot;
import com.koma.appparking.domain.ParkingSpotStatus;
import com.koma.appparking.services.ParkingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all parking spots", description = "Retrieve a list of all parking spots.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of parking spots",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpot.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    ResponseEntity<List<ParkingSpot>> get() {
        return ResponseEntity.ok(parkingSpotService.get());
    }

    @Operation(summary = "Get parking spot by ID", description = "Retrieve a specific parking spot by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved parking spot",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpot.class))),
            @ApiResponse(responseCode = "404", description = "Parking spot not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ParkingSpot> get(@PathVariable Long id) {
        return parkingSpotService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new parking spot", description = "Add a new parking spot to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created parking spot",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpot.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    @PostMapping
    ResponseEntity<ParkingSpot> create(@RequestBody ParkingSpot spot) {
        return ResponseEntity.ok(parkingSpotService.create(spot));
    }

    @Operation(summary = "Update a parking spot", description = "Update details of an existing parking spot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated parking spot",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpot.class))),
            @ApiResponse(responseCode = "404", description = "Parking spot not found", content = @Content)
    })
    @PutMapping("/{id}")
    ResponseEntity<ParkingSpot> update(@PathVariable Long id, @RequestBody ParkingSpot spot) {
        return ResponseEntity.ok(parkingSpotService.update(id, spot));
    }

    @Operation(summary = "Delete a parking spot", description = "Remove a parking spot from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted parking spot", content = @Content),
            @ApiResponse(responseCode = "404", description = "Parking spot not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingSpotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update parking spot status", description = "Change the status of a parking spot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated parking spot status",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpot.class))),
            @ApiResponse(responseCode = "404", description = "Parking spot not found", content = @Content)
    })
    @PatchMapping("/{id}/status")
    ResponseEntity<ParkingSpot> update(@PathVariable Long id, @RequestParam ParkingSpotStatus status) {
        return ResponseEntity.ok(parkingSpotService.update(id, status));
    }
}
