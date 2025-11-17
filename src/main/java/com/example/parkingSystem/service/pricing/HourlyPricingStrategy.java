package com.example.parkingSystem.service.pricing;

import com.example.parkingSystem.domain.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component("hourlyPricing")
public class HourlyPricingStrategy implements PricingStrategy{
    private static final double MOTORCYCLE_RATE = 1.0;
    private static final double CAR_RATE = 2.0;
    private static final double TRUCK_RATE = 3.0;
    private static final double MINIMUM_FEE = 1.0;
    @Override
    public Double paymentAmount(long durationInMinutes, VehicleType vehicleType) {
        double hours = Math.ceil(durationInMinutes / 60.0);

        double hourlyRate = getHourlyRate(vehicleType);

        double fee = hours * hourlyRate;

        return Math.max(fee, MINIMUM_FEE);
    }

    private double getHourlyRate(VehicleType vehicleType) {
        return switch (vehicleType) {
            case MOTORCYCLE -> MOTORCYCLE_RATE;
            case CAR -> CAR_RATE;
            case TRUCK -> TRUCK_RATE;
        };
    }

    @Override
    public String getStrategyName() {
        return "Hourly Pricing";
    }
}
