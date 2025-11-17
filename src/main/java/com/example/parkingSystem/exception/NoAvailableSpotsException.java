package com.example.parkingSystem.exception;

public class NoAvailableSpotsException extends ParkingSystemException {
    public NoAvailableSpotsException(String message) {
        super(message);
    }
}