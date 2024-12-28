package com.koma.appparking.services;

import com.koma.appparking.domain.ParkingLocation;
import com.koma.appparking.repository.ParkingLocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLocationService {

    private final ParkingLocationRepository parkingLocationRepository;

    public ParkingLocationService(ParkingLocationRepository parkingLocationRepository) {
        this.parkingLocationRepository = parkingLocationRepository;
    }

    // Pobierz wszystkie lokalizacje parkingowe
    public List<ParkingLocation> getAllLocations() {
        return parkingLocationRepository.findAll();
    }

    // Pobierz lokalizację parkingową po ID
    public Optional<ParkingLocation> getLocationById(Long id) {
        return parkingLocationRepository.findById(id);
    }

    // Utwórz nową lokalizację parkingową
    public ParkingLocation createLocation(ParkingLocation location) {
        return parkingLocationRepository.save(location);
    }

    // Zaktualizuj istniejącą lokalizację parkingową
    public ParkingLocation updateLocation(Long id, ParkingLocation updatedLocation) {
        return parkingLocationRepository.findById(id)
                .map(existingLocation -> {
                    existingLocation.setStreet(updatedLocation.getStreet());
                    existingLocation.setBuildingNumber(updatedLocation.getBuildingNumber());
                    existingLocation.setCity(updatedLocation.getCity());
                    existingLocation.setZipCode(updatedLocation.getZipCode());
                    return parkingLocationRepository.save(existingLocation);
                })
                .orElseThrow(() -> new IllegalArgumentException("Parking location with ID " + id + " not found"));
    }

    // Usuń lokalizację parkingową
    public void deleteLocation(Long id) {
        if (parkingLocationRepository.existsById(id)) {
            parkingLocationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Parking location with ID " + id + " not found");
        }
    }
}
