package com.koma.appparking.api;

import com.koma.appparking.services.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ParkingController {

    @NotNull
    private final ParkingService parkingService;

    ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Operation(
            summary = "Get the number of free parking spots",
            description = "Returns the number of free parking spots available for a given parking name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the number of free spots"),
            @ApiResponse(responseCode = "400", description = "Invalid parking name provided"),
            @ApiResponse(responseCode = "404", description = "Parking with the given name not found")
    })

    @GetMapping("/parking/free-spots")
    ResponseEntity<Integer> getNumberOfFreeSpotsByParkingName(
            @Parameter(description = "The name of the parking lot", required = true, example = "Main Parking")
            @RequestParam String parkingName) {
        int freeSpots = parkingService.get(parkingName);
        return ResponseEntity.ok(freeSpots);
    }
}
