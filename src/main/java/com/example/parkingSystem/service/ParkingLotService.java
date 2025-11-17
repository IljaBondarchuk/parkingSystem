package com.example.parkingSystem.service;

import com.example.parkingSystem.domain.entities.Level;
import com.example.parkingSystem.domain.entities.ParkingLot;
import com.example.parkingSystem.domain.entities.ParkingSpot;
import com.example.parkingSystem.domain.enums.ParkingSpotType;
import com.example.parkingSystem.exception.ParkingLotNotFoundException;
import com.example.parkingSystem.repository.LevelRepository;
import com.example.parkingSystem.repository.ParkingLotRepository;
import com.example.parkingSystem.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final LevelRepository levelRepository;
    private final ParkingSpotRepository parkingSpotRepository;


    public ParkingLot createParkingLot(String name) {

        if (parkingLotRepository.existsByName(name)) {
            throw new IllegalArgumentException("Parking lot already exists: " + name);
        }

        ParkingLot parkingLot = ParkingLot.builder()
                .name(name)
                .build();

        return parkingLotRepository.save(parkingLot);
    }


    public Level addLevel(Long parkingLotId, Integer levelNumber) {
        ParkingLot parkingLot = findParkingLotById(parkingLotId);

        if (levelRepository.existsByParkingLotIdAndLevelNumber(parkingLotId, levelNumber)) {
            throw new IllegalArgumentException(
                    "Level " + levelNumber + " already exists in parking lot " + parkingLotId
            );
        }

        Level level = Level.builder()
                .levelNumber(levelNumber)
                .parkingLot(parkingLot)
                .build();

        parkingLot.addLevel(level);

        return levelRepository.save(level);
    }

    public void deleteLevel(Long levelId) {
        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new RuntimeException("Level doesnt exists " + levelId));
        ParkingLot parkingLot = level.getParkingLot();
        parkingLot.deleteLevel(level);
    }


    public void addSpotsToLevel(Long levelId, int numberOfSpots, ParkingSpotType spotType) {

        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new RuntimeException("Level doesnt exists " + levelId));

        List<ParkingSpot> existingSpots = parkingSpotRepository.findByLevelId(levelId);
        int maxNumber = existingSpots.stream()
                .mapToInt(ParkingSpot::getParkingSpotNumber)
                .max()
                .orElse(0);


        for (int i = 1; i <= numberOfSpots; i++) {
            ParkingSpot spot = ParkingSpot.builder()
                    .parkingSpotNumber(maxNumber + i)
                    .parkingSpotType(spotType)
                    .level(level)
                    .build();

            level.addParkingSpot(spot);
            parkingSpotRepository.save(spot);
        }

    }

    public void deleteParkingSpot(Long spotId) {
        ParkingSpot spot = parkingSpotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Parking doesnt exists " + spotId));
        Level level = spot.getLevel();
        level.deleteParkingSpot(spot);
        parkingSpotRepository.delete(spot);

    }
    public ParkingLot findParkingLotById(Long id) {
        return parkingLotRepository.findById(id)
                .orElseThrow(() -> new ParkingLotNotFoundException(
                        "Parking lot not found: " + id
                ));
    }


    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    public List<Level> getLevels(Long parkingLotId) {
        return levelRepository.findByParkingLotIdOrderByLevelNumberAsc(parkingLotId);
    }
}