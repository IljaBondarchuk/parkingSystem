package com.example.parkingSystem.domain.entities;

import com.example.parkingSystem.domain.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Pattern(regexp = "^[A-Z]{2}\\d{4}[A-Z]{2}$")
    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

}
