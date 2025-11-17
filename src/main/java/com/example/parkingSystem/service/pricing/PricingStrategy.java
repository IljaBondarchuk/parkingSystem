package com.example.parkingSystem.service.pricing;
import com.example.parkingSystem.domain.enums.VehicleType;

public interface PricingStrategy {
    Double paymentAmount(long durationInMinutes, VehicleType vehicleType);
    String getStrategyName();


}
