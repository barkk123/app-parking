package com.koma.appparking.api;

import com.koma.appparking.services.ParkingService;
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

    @GetMapping("/parking/free-spots")
    ResponseEntity<Integer> getNumberOfFreeSpotsByParkingName(@RequestParam String parkingName) {
        int freeSpots = parkingService.getFreeSpotsByParkingName(parkingName);
        return ResponseEntity.ok(freeSpots);
    }
}
