package com.example.parkingSystem.controller;


import com.example.parkingSystem.domain.entities.ParkingEvent;
import com.example.parkingSystem.dto.request.CheckInRequest;
import com.example.parkingSystem.dto.request.CheckOutRequest;
import com.example.parkingSystem.dto.response.CheckInResponse;
import com.example.parkingSystem.dto.response.CheckOutResponse;
import com.example.parkingSystem.dto.response.ParkingEventResponse;
import com.example.parkingSystem.mapper.ParkingMapper;
import com.example.parkingSystem.service.ParkingEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final ParkingEventService parkingEventService;
    private final ParkingMapper parkingMapper;

    @PostMapping("/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckInResponse checkIn(@Valid @RequestBody CheckInRequest request) {
        ParkingEvent event = parkingEventService.checkIn(
                request.getLicensePlate(),
                request.getVehicleType(),
                request.getParkingLotId(),
                request.getIsHandicapped()
        );
        return parkingMapper.toCheckInResponse(event);
    }

    @PostMapping("/check-out")
    public ResponseEntity<CheckOutResponse> checkOut(@Valid @RequestBody CheckOutRequest request) {
      ParkingEvent event = parkingEventService.checkOut(request.getLicensePlate());
        CheckOutResponse response = parkingMapper.toCheckOutResponse(event);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-out/{ticketNumber}")
    public ResponseEntity<CheckOutResponse> checkOutByTicket(@PathVariable String ticketNumber) {
      ParkingEvent event = parkingEventService.checkOutByTicket(ticketNumber);
        CheckOutResponse response = parkingMapper.toCheckOutResponse(event);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{licensePlate}/active-session")
    public ResponseEntity<ParkingEventResponse> getActiveSession(@PathVariable String licensePlate) {
         ParkingEvent event = parkingEventService.getActiveEvent(licensePlate);
        ParkingEventResponse response = parkingMapper.toParkingEventResponse(event);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{licensePlate}/is-parked")
    public ResponseEntity<Boolean> isParked(@PathVariable String licensePlate) {
        boolean isParked = parkingEventService.isVehicleParked(licensePlate);
        return ResponseEntity.ok(isParked);
    }

    @GetMapping("/{licensePlate}/history")
    public ResponseEntity<List<ParkingEventResponse>> getHistory(@PathVariable String licensePlate) {
        List<ParkingEvent> events = parkingEventService.getVehicleHistory(licensePlate);
        List<ParkingEventResponse> response = events.stream()
                .map(parkingMapper::toParkingEventResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}