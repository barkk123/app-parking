package com.koma.appparking.services;

import com.koma.appparking.domain.ParkingLocation;
import com.koma.appparking.repository.ParkingLocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLocationService {

    private static final Logger logger = LogManager.getLogger(ParkingLocationService.class);

    private final ParkingLocationRepository parkingLocationRepository;

    public ParkingLocationService(ParkingLocationRepository parkingLocationRepository) {
        this.parkingLocationRepository = parkingLocationRepository;
    }

    @Transactional(readOnly = true)
    public List<ParkingLocation> get() {
        logger.info("Fetching all parking locations");
        return parkingLocationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ParkingLocation> get(Long id) {
        logger.info("Fetching parking location with ID {}", id);
        return parkingLocationRepository.findById(id);
    }

    @Transactional
    public ParkingLocation create(ParkingLocation location) {
        logger.info("Creating new parking location: {}", location);
        return parkingLocationRepository.save(location);
    }

    @Transactional
    public ParkingLocation update(Long id, ParkingLocation updatedLocation) {
        logger.info("Updating parking location with ID {}", id);
        return parkingLocationRepository.findById(id)
                .map(existingLocation -> {
                    logger.debug("Found parking location: {}", existingLocation);
                    existingLocation.setStreet(updatedLocation.getStreet());
                    existingLocation.setBuildingNumber(updatedLocation.getBuildingNumber());
                    existingLocation.setCity(updatedLocation.getCity());
                    existingLocation.setZipCode(updatedLocation.getZipCode());
                    logger.info("Updated parking location: {}", existingLocation);
                    return parkingLocationRepository.save(existingLocation);
                })
                .orElseThrow(() -> {
                    logger.error("Parking location with ID {} not found", id);
                    return new EntityNotFoundException("Parking location with ID " + id + " not found");
                });
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting parking location with ID {}", id);
        if (parkingLocationRepository.existsById(id)) {
            parkingLocationRepository.deleteById(id);
            logger.info("Successfully deleted parking location with ID {}", id);
        } else {
            logger.error("Parking location with ID {} not found", id);
            throw new EntityNotFoundException("Parking location with ID " + id + " not found");
        }
    }
}
