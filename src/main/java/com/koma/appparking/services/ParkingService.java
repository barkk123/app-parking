package com.koma.appparking.services;

import com.koma.appparking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true)
    public Integer getNumberOfFreeSpotsByParkingName(String parkingName) {
        return parkingRepository.findFreeSpotsByParkingName(parkingName);
    }
}


