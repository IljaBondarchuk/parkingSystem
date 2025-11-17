package com.example.parkingSystem.mapper;

import com.example.parkingSystem.domain.entities.ParkingEvent;
import com.example.parkingSystem.domain.entities.ParkingLot;
import com.example.parkingSystem.dto.response.*;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class ParkingMapper {

    public CheckInResponse toCheckInResponse(ParkingEvent event) {
        return CheckInResponse.builder()
                .ticketNumber(event.getTicketNumber())
                .licensePlate(event.getVehicle().getLicensePlate())
                .vehicleType(event.getVehicle().getVehicleType())
                .spotNumber(event.getParkingSpot().getParkingSpotNumber())
                .levelNumber(event.getParkingSpot().getLevel().getLevelNumber())
                .spotLocation(event.getParkingSpot().getParkingSpot())
                .checkInTime(event.getCheckInTime())
                .message("Vehicle checkedin sucessfully")
                .build();
    }

    public CheckOutResponse toCheckOutResponse(ParkingEvent event) {
        long durationInMinutes = Duration.between(event.getCheckInTime(), event.getCheckOutTime()).toMinutes();
        String formattedDuration = formatDuration(durationInMinutes);

        return CheckOutResponse.builder()
                .ticketNumber(event.getTicketNumber())
                .licensePlate(event.getVehicle().getLicensePlate())
                .vehicleType(event.getVehicle().getVehicleType())
                .spotNumber(event.getParkingSpot().getParkingSpotNumber())
                .checkInTime(event.getCheckInTime())
                .checkOutTime(event.getCheckOutTime())
                .durationInMinutes(durationInMinutes)
                .formattedDuration(formattedDuration)
                .fee(event.getFee())
                .message("Vehicle checkedout sucessfully. Total fee: $" + event.getFee())
                .build();
    }

    public ParkingEventResponse toParkingEventResponse(ParkingEvent event) {
        Long durationInMinutes = null;
        if (event.getCheckOutTime() != null) {
            durationInMinutes = Duration.between(event.getCheckInTime(), event.getCheckOutTime()).toMinutes();
        }

        return ParkingEventResponse.builder()
                .id(event.getId())
                .ticketNumber(event.getTicketNumber())
                .licensePlate(event.getVehicle().getLicensePlate())
                .vehicleType(event.getVehicle().getVehicleType())
                .spotNumber(event.getParkingSpot().getParkingSpotNumber())
                .levelNumber(event.getParkingSpot().getLevel().getLevelNumber())
                .spotLocation(event.getParkingSpot().getParkingSpot())
                .checkInTime(event.getCheckInTime())
                .checkOutTime(event.getCheckOutTime())
                .isActive(event.getIsActive())
                .fee(event.getFee())
                .durationInMinutes(durationInMinutes)
                .build();
    }

    public ParkingLotResponse toParkingLotResponse(ParkingLot parkingLot, Long availableSpots, Long occupiedSpots) {
        Long totalSpots = availableSpots + occupiedSpots;

        return ParkingLotResponse.builder()
                .id(parkingLot.getId())
                .name(parkingLot.getName())
                .totalLevels(parkingLot.getLevelsList().size())
                .totalSpots(totalSpots)
                .availableSpots(availableSpots)
                .occupiedSpots(occupiedSpots)
                .hasFreeSpots(parkingLot.hasFreeSpot())
                .build();
    }

    private String formatDuration(long minutes) {
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;

        if (hours > 0) {
            return String.format("%d hours %d minutes", hours, remainingMinutes);
        } else {
            return String.format("%d minutes", minutes);
        }
    }
}