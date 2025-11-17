package com.example.parkingSystem.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotResponse {

    private Long id;
    private String name;
    private Integer totalLevels;
    private Long totalSpots;
    private Long availableSpots;
    private Long occupiedSpots;
    private Boolean hasFreeSpots;
}