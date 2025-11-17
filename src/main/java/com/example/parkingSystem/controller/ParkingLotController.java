package com.example.parkingSystem.controller;

import com.example.parkingSystem.domain.entities.Level;
import com.example.parkingSystem.domain.entities.ParkingEvent;
import com.example.parkingSystem.domain.entities.ParkingLot;
import com.example.parkingSystem.domain.entities.ParkingSpot;
import com.example.parkingSystem.dto.request.AddSpotsRequest;
import com.example.parkingSystem.dto.request.CreateLevelRequest;
import com.example.parkingSystem.dto.request.CreateParkingLotRequest;
import com.example.parkingSystem.dto.response.ParkingEventResponse;
import com.example.parkingSystem.dto.response.ParkingLotResponse;
import com.example.parkingSystem.mapper.ParkingMapper;
import com.example.parkingSystem.service.ParkingEventService;
import com.example.parkingSystem.service.ParkingLotService;
import com.example.parkingSystem.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;
    private final ParkingEventService parkingEventService;
    private final ParkingMapper parkingMapper;

    @PostMapping
    public ResponseEntity<ParkingLot> createParkingLot(@Valid @RequestBody CreateParkingLotRequest request) {
        ParkingLot parkingLot = parkingLotService.createParkingLot(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingLot);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotResponse> getParkingLot(@PathVariable Long id) {
        ParkingLot parkingLot = parkingLotService.findParkingLotById(id);
        long availableSpots = parkingSpotService.countAvailableSpots(id);
        long occupiedSpots = parkingLot.getLevelsList().stream()
                .flatMap(level -> level.getParkingSpotsList().stream())
                .filter(spot -> !spot.isAvaliable())
                .count();

        ParkingLotResponse response = parkingMapper.toParkingLotResponse(parkingLot, availableSpots, occupiedSpots);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/levels")
    public ResponseEntity<Level> addLevel(@PathVariable Long id, @Valid @RequestBody CreateLevelRequest request) {
         Level level = parkingLotService.addLevel(id, request.getLevelNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(level);
    }

    @DeleteMapping("/levels/{levelId}")
    public ResponseEntity<String> deleteLevel(@PathVariable Long levelId) {
        parkingLotService.deleteLevel(levelId);
        return ResponseEntity.ok("Level deleted successfully");
    }

    @GetMapping("/{id}/levels")
    public ResponseEntity<List<Level>> getLevels(@PathVariable Long id) {
       List<Level> levels = parkingLotService.getLevels(id);
        return ResponseEntity.ok(levels);
    }

    @PostMapping("/levels/{levelId}/spots")
    public ResponseEntity<String> addSpots(@PathVariable Long levelId, @Valid @RequestBody AddSpotsRequest request) {
        parkingLotService.addSpotsToLevel(levelId, request.getNumberOfSpots(), request.getSpotType());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Added " + request.getNumberOfSpots() + " spots successfully");
    }

    @GetMapping("/{id}/available-spots")
    public ResponseEntity<List<ParkingSpot>> getAvailableSpots(@PathVariable Long id) {
        List<ParkingSpot> spots = parkingSpotService.getAvailableSpots(id);
        return ResponseEntity.ok(spots);
    }
    @DeleteMapping("/spots/{spotId}")
    public ResponseEntity<String> deleteParkingSpot(@PathVariable Long spotId) {
        parkingLotService.deleteParkingSpot(spotId);
        return ResponseEntity.ok("Parking spot deleted successfully");
    }
    @GetMapping("/{id}/active-sessions")
    public ResponseEntity<List<ParkingEventResponse>> getActiveSessions(@PathVariable Long id) {

        List<ParkingEvent> events = parkingEventService.getActiveEventsByParkingLot(id);
        List<ParkingEventResponse> response = events.stream()
                .map(parkingMapper::toParkingEventResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}