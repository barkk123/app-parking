package com.koma.appparking.services;

import com.koma.appparking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class ParkingService {

    private static final Logger logger = LogManager.getLogger(ParkingService.class);

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true)
    public Integer getNumberOfFreeSpotsByParkingName(String parkingName) {
        logger.info("Fetching number of free spots for parking: {}", parkingName);

        Integer freeSpots = parkingRepository.findFreeSpotsByParkingName(parkingName);

        if (freeSpots != null) {
            logger.info("Number of free spots at parking '{}' is {}", parkingName, freeSpots);
        } else {
            logger.warn("Parking '{}' not found or free spots information is unavailable", parkingName);
        }

        return freeSpots;
    }
}
