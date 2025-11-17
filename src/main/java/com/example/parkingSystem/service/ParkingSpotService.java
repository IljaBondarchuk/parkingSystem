package com.example.parkingSystem.service;
import com.example.parkingSystem.domain.entities.ParkingSpot;
import com.example.parkingSystem.domain.enums.ParkingSpotStatus;
import com.example.parkingSystem.domain.enums.ParkingSpotType;
import com.example.parkingSystem.domain.enums.VehicleType;
import com.example.parkingSystem.exception.NoAvailableSpotsException;
import com.example.parkingSystem.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;
    public ParkingSpot findAvailableSpot(Long parkingLotId, VehicleType vehicleType, boolean isHandicapped) {
        if (isHandicapped) {
            ParkingSpot handicappedSpot = findAvailableSpotByType(
                    parkingLotId,
                    ParkingSpotType.HANDICAPPED
            );
            if (handicappedSpot != null) {
                return handicappedSpot;
            }

        }

        ParkingSpotType requiredType = getRequiredSpotType(vehicleType);

        ParkingSpot spot = findAvailableSpotByType(parkingLotId, requiredType);

        if (spot == null) {
            spot = findLargerAvailableSpot(parkingLotId, vehicleType);
        }

        if (spot == null) {
            throw new NoAvailableSpotsException(
                    "No available parking spots for " + vehicleType + " in parking lot " + parkingLotId
            );
        }
        return spot;
    }


    private ParkingSpot findAvailableSpotByType(Long parkingLotId, ParkingSpotType spotType) {
        return parkingSpotRepository
                .findFirstAvailableSpotByType(parkingLotId, spotType)
                .orElse(null);
    }


    private ParkingSpot findLargerAvailableSpot(Long parkingLotId, VehicleType vehicleType) {
        return switch (vehicleType) {
            case MOTORCYCLE ->
                    findAvailableSpotByType(parkingLotId, ParkingSpotType.COMPACT) != null
                            ? findAvailableSpotByType(parkingLotId, ParkingSpotType.COMPACT)
                            : findAvailableSpotByType(parkingLotId, ParkingSpotType.LARGE);

            case CAR ->
                    findAvailableSpotByType(parkingLotId, ParkingSpotType.LARGE);

            case TRUCK ->
                    null;
        };
    }


    private ParkingSpotType getRequiredSpotType(VehicleType vehicleType) {
        return switch (vehicleType) {
            case MOTORCYCLE -> ParkingSpotType.MOTORCYCLE;
            case CAR -> ParkingSpotType.COMPACT;
            case TRUCK -> ParkingSpotType.LARGE;
        };
    }


    public void occupySpot(ParkingSpot spot) {
         spot.setOccupied();
        parkingSpotRepository.save(spot);
    }

    public void releaseSpot(ParkingSpot spot) {
        spot.setAvalible();
        parkingSpotRepository.save(spot);
    }

    public void markSpotForMaintenance(Long spotId) {
        ParkingSpot spot = parkingSpotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Parking spot not found: " + spotId));

        spot.setMaintenance();
        parkingSpotRepository.save(spot);
    }

    public void markSpotAsAvailable(Long spotId) {
        ParkingSpot spot = parkingSpotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Parking spot not found: " + spotId));

        spot.setAvalible();
        parkingSpotRepository.save(spot);
    }


    public long countAvailableSpots(Long parkingLotId) {
        return parkingSpotRepository.countAvailableSpots(parkingLotId);
    }


    public List<ParkingSpot> getAvailableSpots(Long parkingLotId) {
        return parkingSpotRepository.findAvailableSpotsByParkingLotId(parkingLotId);
    }
}
