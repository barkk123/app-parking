package com.koma.appparking.services;

import com.koma.appparking.domain.ParkingLocation;
import com.koma.appparking.repository.ParkingLocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLocationService {

    private final ParkingLocationRepository parkingLocationRepository;

    public ParkingLocationService(ParkingLocationRepository parkingLocationRepository) {
        this.parkingLocationRepository = parkingLocationRepository;
    }

    @Transactional(readOnly = true)
    public List<ParkingLocation> getAllLocations() {
        return parkingLocationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ParkingLocation> getLocationById(Long id) {
        return parkingLocationRepository.findById(id);
    }

    @Transactional
    public ParkingLocation createLocation(ParkingLocation location) {
        return parkingLocationRepository.save(location);
    }

    @Transactional
    public ParkingLocation update(Long id, ParkingLocation updatedLocation) {
        return parkingLocationRepository.findById(id)
                .map(existingLocation -> {
                    existingLocation.setStreet(updatedLocation.getStreet());
                    existingLocation.setBuildingNumber(updatedLocation.getBuildingNumber());
                    existingLocation.setCity(updatedLocation.getCity());
                    existingLocation.setZipCode(updatedLocation.getZipCode());
                    return parkingLocationRepository.save(existingLocation);
                })
                .orElseThrow(() -> new EntityNotFoundException("Parking location with ID " + id + " not found"));
    }

    @Transactional
    public void deleteLocation(Long id) {
        if (parkingLocationRepository.existsById(id)) {
            parkingLocationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Parking location with ID " + id + " not found");
        }
    }
}
