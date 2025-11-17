package com.example.parkingSystem.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "parking_events")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ParkingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String ticketNumber;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "parking_slot_id", nullable = false)
    private ParkingSpot parkingSpot;
    @Column(nullable = false)
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Double fee;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    public void checkOut(Double paymentAmount){
        this.checkOutTime = LocalDateTime.now();
        this.fee = paymentAmount;
        this.isActive = false;
    }

    private String generateTicketNumber(){
        return "TT-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000000);
    }
    @PrePersist
    public void setUpEvent() {
        if (this.ticketNumber == null) {
            this.ticketNumber = generateTicketNumber();
        }
        if (this.checkInTime == null) {
            this.checkInTime = LocalDateTime.now();
        }
    }

}
