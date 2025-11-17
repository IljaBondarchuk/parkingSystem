package com.example.parkingSystem.exception;

public class VehicleNotFoundException extends ParkingSystemException {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}