package com.example.parkingSystem.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "levels")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(nullable = false)
    private Integer levelNumber;

@ManyToOne
@JoinColumn(name="parking_lot_id", nullable = false)
private ParkingLot parkingLot;

@OneToMany(mappedBy = "level")
@Builder.Default
private List<ParkingSpot> parkingSpotsList = new ArrayList<>();

public int getSize(){
    return parkingSpotsList.size();
}
public long getAvalibleParkingSpots(){
    return parkingSpotsList.stream()
            .filter(ParkingSpot::isAvaliable)
            .count();
}
public void addParkingSpot(ParkingSpot parkingSpot){
    parkingSpotsList.add(parkingSpot);
    parkingSpot.setLevel(this);
}
    public void deleteParkingSpot(ParkingSpot parkingSpot){
        parkingSpotsList.remove(parkingSpot);
        parkingSpot.setLevel(null);
    }
}
