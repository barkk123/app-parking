package com.koma.appparking.api;

import com.koma.appparking.services.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parking/free-spots")
    public ResponseEntity<Integer> getFreeSpots(@RequestParam String parkingName) {
        int freeSpots = parkingService.getFreeSpotsByParkingName(parkingName);
        return ResponseEntity.ok(freeSpots);
    }
}
