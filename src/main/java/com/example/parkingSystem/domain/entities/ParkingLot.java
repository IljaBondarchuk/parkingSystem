package com.example.parkingSystem.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lot")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
 private String name;

@Builder.Default
 @OneToMany(mappedBy = "parkingLot")
 private List<Level> levelsList = new ArrayList<>();

public void addLevel(Level level){
    levelsList.add(level);
    level.setParkingLot(this);
}

    public void deleteLevel(Level level){
        levelsList.remove(level);
        level.setParkingLot(null);
    }
    public long getAvalibleParkingSpots(){
    return levelsList.stream()
            .mapToLong(Level::getAvalibleParkingSpots)
            .sum();
    }

    public boolean hasFreeSpot(){
    return getAvalibleParkingSpots() > 0;
    }



}
