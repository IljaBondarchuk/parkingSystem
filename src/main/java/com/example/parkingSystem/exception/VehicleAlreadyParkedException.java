package com.example.parkingSystem.exception;

public class VehicleAlreadyParkedException extends ParkingSystemException {
    public VehicleAlreadyParkedException(String message) {
        super(message);
    }
}