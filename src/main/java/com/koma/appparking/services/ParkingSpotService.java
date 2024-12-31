package com.koma.appparking.services;

import com.koma.appparking.domain.ParkingSpot;
import com.koma.appparking.domain.ParkingSpotStatus;
import com.koma.appparking.repository.ParkingSpotRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional(readOnly = true)
    public List<ParkingSpot> getAllSpots() {
        return parkingSpotRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ParkingSpot> getSpotById(Long id) {
        return parkingSpotRepository.findById(id);
    }

    @Transactional
    public ParkingSpot createSpot(ParkingSpot spot) {
        return parkingSpotRepository.save(spot);
    }

    @Transactional
    public ParkingSpot updateSpot(Long id, ParkingSpot updatedSpot) {
        return parkingSpotRepository.findById(id)
                .map(existingSpot -> {
                    existingSpot.setNumber(updatedSpot.getNumber());
                    existingSpot.setStatus(updatedSpot.getStatus());
                    existingSpot.setParking(updatedSpot.getParking());
                    return parkingSpotRepository.save(existingSpot);
                })
                .orElseThrow(() -> new EntityNotFoundException("Parking spot with ID " + id + " not found"));
    }

    @Transactional
    public void deleteSpot(Long id) {
        if (parkingSpotRepository.existsById(id)) {
            parkingSpotRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Parking spot with ID " + id + " not found");
        }
    }


    public ParkingSpot updateSpotStatus(Long id, ParkingSpotStatus status) {
        return parkingSpotRepository.findById(id)
                .map(spot -> {
                    spot.setStatus(status);
                    return parkingSpotRepository.save(spot);
                })
                .orElseThrow(() -> new EntityNotFoundException("Parking spot with ID " + id + " not found"));
    }
}
