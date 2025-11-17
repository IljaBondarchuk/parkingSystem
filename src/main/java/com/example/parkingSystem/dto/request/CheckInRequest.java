package com.example.parkingSystem.dto.request;

import com.example.parkingSystem.domain.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInRequest {

    @NotBlank(message = "License plate is required")
    @Pattern(
            regexp = "^[A-Z]{2}\\d{4}[A-Z]{2}$",
            message = "License plate format: AA1234BB"
    )
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    @NotNull(message = "Parking lot ID is required")
    private Long parkingLotId;

    @Builder.Default
    private Boolean isHandicapped = false;
}