package com.koma.appparking.services;

import com.koma.appparking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public Integer getFreeSpotsByParkingName(String parkingName) {
        return parkingRepository.findFreeSpotsByParkingName(parkingName);
    }
}


