package com.example.parkingSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLevelRequest {

    @NotNull(message = "Level number is required")
    private Integer levelNumber;

    @NotNull(message = "Parking lot Id is required")
    private Long parkingLotId;
}
