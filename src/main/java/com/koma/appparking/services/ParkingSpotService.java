package com.koma.appparking.services;

import com.koma.appparking.domain.ParkingSpot;
import com.koma.appparking.domain.ParkingSpotStatus;
import com.koma.appparking.repository.ParkingSpotRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    private static final Logger logger = LogManager.getLogger(ParkingSpotService.class);

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional(readOnly = true)
    public List<ParkingSpot> get() {
        logger.info("Fetching all parking spots");
        List<ParkingSpot> spots = parkingSpotRepository.findAll();
        logger.info("Found {} parking spots", spots.size());
        return spots;
    }

    @Transactional(readOnly = true)
    public Optional<ParkingSpot> get(Long id) {
        logger.info("Fetching parking spot with ID {}", id);
        Optional<ParkingSpot> spot = parkingSpotRepository.findById(id);
        if (spot.isPresent()) {
            logger.info("Found parking spot: {}", spot.get());
        } else {
            logger.warn("Parking spot with ID {} not found", id);
        }
        return spot;
    }

    @Transactional
    public ParkingSpot create(ParkingSpot spot) {
        logger.info("Creating new parking spot: {}", spot);
        ParkingSpot savedSpot = parkingSpotRepository.save(spot);
        logger.info("Created parking spot with ID {}", savedSpot.getId());
        return savedSpot;
    }

    @Transactional
    public ParkingSpot update(Long id, ParkingSpot updatedSpot) {
        logger.info("Updating parking spot with ID {}", id);
        return parkingSpotRepository.findById(id)
                .map(existingSpot -> {
                    logger.debug("Existing parking spot: {}", existingSpot);
                    existingSpot.setNumber(updatedSpot.getNumber());
                    existingSpot.setStatus(updatedSpot.getStatus());
                    existingSpot.setParking(updatedSpot.getParking());
                    ParkingSpot savedSpot = parkingSpotRepository.save(existingSpot);
                    logger.info("Updated parking spot: {}", savedSpot);
                    return savedSpot;
                })
                .orElseThrow(() -> {
                    logger.error("Parking spot with ID {} not found", id);
                    return new EntityNotFoundException("Parking spot with ID " + id + " not found");
                });
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting parking spot with ID {}", id);
        if (parkingSpotRepository.existsById(id)) {
            parkingSpotRepository.deleteById(id);
            logger.info("Successfully deleted parking spot with ID {}", id);
        } else {
            logger.error("Parking spot with ID {} not found", id);
            throw new EntityNotFoundException("Parking spot with ID " + id + " not found");
        }
    }

    @Transactional
    public ParkingSpot update(Long id, ParkingSpotStatus status) {
        logger.info("Updating status of parking spot with ID {} to {}", id, status);
        return parkingSpotRepository.findById(id)
                .map(spot -> {
                    spot.setStatus(status);
                    ParkingSpot savedSpot = parkingSpotRepository.save(spot);
                    logger.info("Updated parking spot status: {}", savedSpot);
                    return savedSpot;
                })
                .orElseThrow(() -> {
                    logger.error("Parking spot with ID {} not found", id);
                    return new EntityNotFoundException("Parking spot with ID " + id + " not found");
                });
    }
}
