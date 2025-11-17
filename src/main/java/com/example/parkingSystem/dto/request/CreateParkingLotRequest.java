package com.example.parkingSystem.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateParkingLotRequest {

    @NotBlank(message = "Parking lot name is required")
    private String name;
}