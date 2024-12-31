package com.koma.appparking.services;

import com.koma.appparking.domain.Vehicle;
import com.koma.appparking.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> getVehicleByLicenseNumber(String licenseNumber) {
        return vehicleRepository.findById(licenseNumber);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(String licenseNumber, Vehicle updatedVehicle) {
        return vehicleRepository.findById(licenseNumber)
                .map(existingVehicle -> {
                    existingVehicle.setMark(updatedVehicle.getMark());
                    existingVehicle.setModel(updatedVehicle.getModel());
                    return vehicleRepository.save(existingVehicle);
                })
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with license number " + licenseNumber + " not found"));
    }

    public void deleteVehicle(String licenseNumber) {
        if (vehicleRepository.existsById(licenseNumber)) {
            vehicleRepository.deleteById(licenseNumber);
        } else {
            throw new EntityNotFoundException("Vehicle with license number " + licenseNumber + " not found");
        }
    }
}
