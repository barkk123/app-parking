package com.koma.appparking.services;

import com.koma.appparking.domain.ParkingSpot;
import com.koma.appparking.domain.ParkingSpotStatus;
import com.koma.appparking.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    // Pobierz wszystkie miejsca parkingowe
    public List<ParkingSpot> getAllSpots() {
        return parkingSpotRepository.findAll();
    }

    // Pobierz miejsce parkingowe po ID
    public Optional<ParkingSpot> getSpotById(Long id) {
        return parkingSpotRepository.findById(id);
    }

    // Utwórz nowe miejsce parkingowe
    public ParkingSpot createSpot(ParkingSpot spot) {
        return parkingSpotRepository.save(spot);
    }

    // Zaktualizuj istniejące miejsce parkingowe
    public ParkingSpot updateSpot(Long id, ParkingSpot updatedSpot) {
        return parkingSpotRepository.findById(id)
                .map(existingSpot -> {
                    existingSpot.setNumber(updatedSpot.getNumber());
                    existingSpot.setStatus(updatedSpot.getStatus());
                    existingSpot.setParking(updatedSpot.getParking());
                    return parkingSpotRepository.save(existingSpot);
                })
                .orElseThrow(() -> new IllegalArgumentException("Parking spot with ID " + id + " not found"));
    }

    // Usuń miejsce parkingowe
    public void deleteSpot(Long id) {
        if (parkingSpotRepository.existsById(id)) {
            parkingSpotRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Parking spot with ID " + id + " not found");
        }
    }

    // Zaktualizuj status miejsca parkingowego
    public ParkingSpot updateSpotStatus(Long id, ParkingSpotStatus status) {
        return parkingSpotRepository.findById(id)
                .map(spot -> {
                    spot.setStatus(status);
                    return parkingSpotRepository.save(spot);
                })
                .orElseThrow(() -> new IllegalArgumentException("Parking spot with ID " + id + " not found"));
    }
}
