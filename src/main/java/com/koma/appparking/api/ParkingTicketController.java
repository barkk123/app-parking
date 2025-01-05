package com.koma.appparking.api;

import com.koma.appparking.domain.ParkingTicket;
import com.koma.appparking.services.ParkingTicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking/parking-tickets")
@Validated
class ParkingTicketController {

    @NotNull
    private final ParkingTicketService parkingTicketService;

     ParkingTicketController(ParkingTicketService parkingTicketService) {
        this.parkingTicketService = parkingTicketService;
    }

    @Operation(summary = "Get all parking tickets", description = "Retrieve a list of all parking tickets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of parking tickets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingTicket.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
     ResponseEntity<List<ParkingTicket>> get() {
        return ResponseEntity.ok(parkingTicketService.get());
    }

    @Operation(summary = "Get parking ticket by ID", description = "Retrieve a specific parking ticket by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved parking ticket",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingTicket.class))),
            @ApiResponse(responseCode = "404", description = "Parking ticket not found", content = @Content)
    })
    @GetMapping("/{id}")
     ResponseEntity<ParkingTicket> get(@PathVariable Long id) {
        return parkingTicketService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new parking ticket", description = "Create a parking ticket for a vehicle.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created parking ticket",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingTicket.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    @PostMapping
     ResponseEntity<ParkingTicket> create(@RequestBody @Valid ParkingTicketCreateModel model) {
        return ResponseEntity.ok(parkingTicketService.createParkingTicket(model));
    }

    @Operation(summary = "Delete a parking ticket", description = "Delete an existing parking ticket by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted parking ticket", content = @Content),
            @ApiResponse(responseCode = "404", description = "Parking ticket not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingTicketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Pay for a parking ticket",
            description = "Pay for an active parking ticket associated with a specific parking spot and vehicle's license number. The parking spot will be released after payment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully paid for the parking ticket",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "Active parking ticket not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PatchMapping("/{parkingSpotId}/pay")
    ResponseEntity<Double> payForParkingTicket(
            @PathVariable Long parkingSpotId,
            @RequestParam String licenseNumber) {
        double totalCharge = parkingTicketService.payForParkingTicket(licenseNumber, parkingSpotId);
        return ResponseEntity.ok(totalCharge);
    }

    @Operation(
            summary = "Pay for a parking ticket (alternative endpoint)",
            description = "Alternative endpoint to pay for an active parking ticket using query parameters for license number and parking spot ID. The parking spot will be released after payment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully paid for the parking ticket",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "Active parking ticket not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/pay")
    public ResponseEntity<Double> payForParkingTicket(
            @RequestParam String licenseNumber,
            @RequestParam Long parkingSpotId) {
        double totalCharge = parkingTicketService.payForParkingTicket(licenseNumber, parkingSpotId);
        return ResponseEntity.ok(totalCharge);
    }

}
