package com.example.parkingSystem.service.pricing;

import com.example.parkingSystem.domain.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component("fixedPricing")
public class FixRatePricingStrategy implements PricingStrategy{
    private static final double MOTORCYCLE_FLAT_RATE = 5.0;
    private static final double CAR_FLAT_RATE = 10.0;
    private static final double TRUCK_FLAT_RATE = 15.0;

    @Override
    public Double paymentAmount(long durationInMinutes, VehicleType vehicleType) {
        return switch (vehicleType) {
            case MOTORCYCLE -> MOTORCYCLE_FLAT_RATE;
            case CAR -> CAR_FLAT_RATE;
            case TRUCK -> TRUCK_FLAT_RATE;
        };
    }

    @Override
    public String getStrategyName() {
        return "Fix Rate Pricing";
    }
}
