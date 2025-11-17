package com.example.parkingSystem.dto.request;

import com.example.parkingSystem.domain.enums.ParkingSpotType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddSpotsRequest {

    @NotNull(message = "Level ID is required")
    private Long levelId;

    @NotNull(message = "Number of spots is required")
    @Min(value = 1, message = "Number of spots must be at least 1")
    private Integer numberOfSpots;

    @NotNull(message = "Spot type is required")
    private ParkingSpotType spotType;
}