package com.example.parkingSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOutRequest {

    @NotBlank(message = "License plate is required")
    private String licensePlate;
}