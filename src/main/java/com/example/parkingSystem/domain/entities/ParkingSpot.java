package com.example.parkingSystem.domain.entities;

import com.example.parkingSystem.domain.enums.ParkingSpotStatus;
import com.example.parkingSystem.domain.enums.ParkingSpotType;
import com.example.parkingSystem.domain.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "parking_spots")
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer parkingSpotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingSpotType parkingSpotType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ParkingSpotStatus status = ParkingSpotStatus.AVAILABLE;
    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    public Boolean isAvaliable() {
        return status == ParkingSpotStatus.AVAILABLE;
    }

    public void setMaintenance() {
        this.status = ParkingSpotStatus.MAINTENANCE;
    }

    public void setOccupied() {
        this.status = ParkingSpotStatus.OCCUPIED;
    }

    public void setAvalible() {
        this.status = ParkingSpotStatus.AVAILABLE;
    }

    public String getParkingSpot(){
        return "Level " + level.getLevelNumber() + "  №" + parkingSpotNumber;
    }
    public boolean isFit(VehicleType vehicleType, boolean isHandicapped) {
        if (isHandicapped && parkingSpotType == ParkingSpotType.HANDICAPPED) {
            return true;
        }
        return switch (vehicleType) {
            case MOTORCYCLE -> parkingSpotType == ParkingSpotType.MOTORCYCLE
                    || parkingSpotType == ParkingSpotType.COMPACT
                    || parkingSpotType == ParkingSpotType.LARGE;

            case CAR -> parkingSpotType == ParkingSpotType.COMPACT
                    || parkingSpotType == ParkingSpotType.LARGE;

            case TRUCK -> parkingSpotType == ParkingSpotType.LARGE;
            };
        }

        public boolean isFit(VehicleType vehicleType){
        return isFit(vehicleType, false);
        }
    }
