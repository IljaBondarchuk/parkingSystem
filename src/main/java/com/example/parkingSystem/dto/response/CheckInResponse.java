package com.example.parkingSystem.dto.response;

import com.example.parkingSystem.domain.enums.VehicleType;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInResponse {

    private String ticketNumber;
    private String licensePlate;
    private VehicleType vehicleType;
    private Integer spotNumber;
    private Integer levelNumber;
    private String spotLocation;
    private LocalDateTime checkInTime;
    private String message;
}