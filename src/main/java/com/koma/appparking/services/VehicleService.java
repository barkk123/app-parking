package com.koma.appparking.services;

import com.koma.appparking.domain.Vehicle;
import com.koma.appparking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Pobierz wszystkie pojazdy
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Pobierz pojazd po numerze rejestracyjnym
    public Optional<Vehicle> getVehicleByLicenseNumber(String licenseNumber) {
        return vehicleRepository.findById(licenseNumber);
    }

    // Utwórz nowy pojazd
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Zaktualizuj istniejący pojazd
    public Vehicle updateVehicle(String licenseNumber, Vehicle updatedVehicle) {
        return vehicleRepository.findById(licenseNumber)
                .map(existingVehicle -> {
                    existingVehicle.setMark(updatedVehicle.getMark());
                    existingVehicle.setModel(updatedVehicle.getModel());
                    return vehicleRepository.save(existingVehicle);
                })
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with license number " + licenseNumber + " not found"));
    }

    // Usuń pojazd
    public void deleteVehicle(String licenseNumber) {
        if (vehicleRepository.existsById(licenseNumber)) {
            vehicleRepository.deleteById(licenseNumber);
        } else {
            throw new IllegalArgumentException("Vehicle with license number " + licenseNumber + " not found");
        }
    }
}
