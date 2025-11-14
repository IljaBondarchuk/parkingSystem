package com.example.parkingSystem.repository;

import com.example.parkingSystem.domain.entities.Vehicle;
import com.example.parkingSystem.domain.enums.VehicleType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository {
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    List<Vehicle> findByVehicleType(VehicleType vehicleType);
    boolean existsByLicensePlate(String licensePlate);


}
