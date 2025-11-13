package com.example.parkingSystem.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lot")

public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
 private String name;


}
