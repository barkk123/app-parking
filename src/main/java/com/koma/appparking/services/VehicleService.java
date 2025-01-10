package com.koma.appparking.services;

import com.koma.appparking.domain.Vehicle;
import com.koma.appparking.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> get() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> get(String licenseNumber) {
        return vehicleRepository.findById(licenseNumber);
    }

    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(String licenseNumber, Vehicle updatedVehicle) {
        log.info("Updating vehicle with license number: {}", licenseNumber);
        return vehicleRepository.findById(licenseNumber)
                .map(existingVehicle -> {
                    log.debug("Updating vehicle details: {}", existingVehicle);
                    existingVehicle.setMark(updatedVehicle.getMark());
                    existingVehicle.setModel(updatedVehicle.getModel());
                    return vehicleRepository.save(existingVehicle);
                })
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with license number " + licenseNumber + " not found"));
    }

    public void delete(String licenseNumber) {
        log.info("Deleting vehicle with license number: {}", licenseNumber);
        if (vehicleRepository.existsById(licenseNumber)) {
            vehicleRepository.deleteById(licenseNumber);
            log.info("Successfully deleted vehicle with license number: {}", licenseNumber);
        } else {
            log.warn("Attempted to delete non-existent vehicle with license number: {}", licenseNumber);
            throw new EntityNotFoundException("Vehicle with license number " + licenseNumber + " not found");
        }
    }
}
