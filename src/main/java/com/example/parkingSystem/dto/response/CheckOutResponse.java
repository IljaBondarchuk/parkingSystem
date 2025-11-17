package com.example.parkingSystem.dto.response;
import com.example.parkingSystem.domain.enums.VehicleType;
import lombok.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOutResponse {

    private String ticketNumber;
    private String licensePlate;
    private VehicleType vehicleType;
    private Integer spotNumber;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Long durationInMinutes;
    private String formattedDuration;
    private Double fee;
    private String message;
}