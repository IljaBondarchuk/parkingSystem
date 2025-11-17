package com.example.parkingSystem.service;

import com.example.parkingSystem.domain.entities.Vehicle;
import com.example.parkingSystem.exception.VehicleNotFoundException;
import com.example.parkingSystem.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.parkingSystem.domain.enums.VehicleType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public Vehicle findOrCreateVehicle(String licensePlate, VehicleType vehicleType) {
             return vehicleRepository.findByLicensePlate(licensePlate)
                .orElseGet(() -> {
                    Vehicle newVehicle = Vehicle.builder()
                            .licensePlate(licensePlate)
                            .vehicleType(vehicleType)
                            .build();
                    return vehicleRepository.save(newVehicle);
                });
    }
    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new VehicleNotFoundException(
                        "Vehicle not found: " + licensePlate
                ));
    }
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }


    public List<Vehicle> getVehiclesByType(VehicleType vehicleType) {
        return vehicleRepository.findByVehicleType(vehicleType);
    }


    public boolean vehicleExists(String licensePlate) {
        return vehicleRepository.existsByLicensePlate(licensePlate);
    }
}
