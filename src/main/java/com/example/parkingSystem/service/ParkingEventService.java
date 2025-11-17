package com.example.parkingSystem.service;

import com.example.parkingSystem.domain.entities.ParkingEvent;
import com.example.parkingSystem.domain.entities.ParkingSpot;
import com.example.parkingSystem.domain.entities.Vehicle;
import com.example.parkingSystem.domain.enums.VehicleType;
import com.example.parkingSystem.exception.ParkingEventNotFoundException;
import com.example.parkingSystem.exception.VehicleAlreadyParkedException;
import com.example.parkingSystem.repository.ParkingEventRepository;
import com.example.parkingSystem.service.pricing.PricingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ParkingEventService {

    private final ParkingEventRepository parkingEventRepository;
    private final VehicleService vehicleService;
    private final ParkingSpotService parkingSpotService;
    private final PricingStrategy pricingStrategy;
    public ParkingEventService(
            ParkingEventRepository parkingEventRepository,
            VehicleService vehicleService,
            ParkingSpotService parkingSpotService,
            @Qualifier("hourlyPricing") PricingStrategy pricingStrategy //change strategy
    ) {
        this.parkingEventRepository = parkingEventRepository;
        this.vehicleService = vehicleService;
        this.parkingSpotService = parkingSpotService;
        this.pricingStrategy = pricingStrategy;
    }
    public ParkingEvent checkIn(String licensePlate, VehicleType vehicleType,
                                Long parkingLotId, boolean isHandicapped) {

          if (parkingEventRepository.hasActiveEvent(licensePlate)) {
            throw new VehicleAlreadyParkedException(
                    "Vehicle " + licensePlate + " is already parked"
            );
        }

       Vehicle vehicle = vehicleService.findOrCreateVehicle(licensePlate, vehicleType);

        ParkingSpot spot = parkingSpotService.findAvailableSpot(
                parkingLotId,
                vehicleType,
                isHandicapped
        );

         if (!spot.isFit(vehicleType, isHandicapped)) {
            throw new IllegalStateException(
                    "Spot " + spot.getParkingSpot() + " is not suitable for " + vehicleType
            );
        }

         parkingSpotService.occupySpot(spot);
         ParkingEvent event = ParkingEvent.builder()
                .vehicle(vehicle)
                .parkingSpot(spot)
                .build();

        ParkingEvent savedEvent = parkingEventRepository.save(event);

        return savedEvent;
    }


    public ParkingEvent checkOut(String licensePlate) {

         ParkingEvent event = parkingEventRepository
                .findActiveEventByLicensePlate(licensePlate)
                .orElseThrow(() -> new ParkingEventNotFoundException(
                        "No active parking event found for vehicle: " + licensePlate
                ));

   long durationInMinutes = calculateDurationInMinutes(event.getCheckInTime());


          Double fee = pricingStrategy.paymentAmount(
                durationInMinutes,
                event.getVehicle().getVehicleType()
        );
         event.checkOut(fee);

        parkingSpotService.releaseSpot(event.getParkingSpot());

          ParkingEvent completedEvent = parkingEventRepository.save(event);

        return completedEvent;
    }

    public ParkingEvent checkOutByTicket(String ticketNumber) {
        ParkingEvent event = parkingEventRepository
                .findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new ParkingEventNotFoundException(
                        "Parking event not found for ticket: " + ticketNumber
                ));

        if (!event.getIsActive()) {
            throw new IllegalStateException(
                    "Parking event already completed for ticket: " + ticketNumber
            );
        }

        return checkOut(event.getVehicle().getLicensePlate());
    }

    public ParkingEvent getActiveEvent(String licensePlate) {
        return parkingEventRepository
                .findActiveEventByLicensePlate(licensePlate)
                .orElseThrow(() -> new ParkingEventNotFoundException(
                        "No active parking event for vehicle: " + licensePlate
                ));
    }
    public List<ParkingEvent> getAllActiveEvents() {
        return parkingEventRepository.findByIsActiveTrue();
    }

    public List<ParkingEvent> getActiveEventsByParkingLot(Long parkingLotId) {
        return parkingEventRepository.findActiveEventsByParkingLotId(parkingLotId);
    }

    public List<ParkingEvent> getVehicleHistory(String licensePlate) {
        return parkingEventRepository.findByLicensePlate(licensePlate);
    }



    private long calculateDurationInMinutes(LocalDateTime checkInTime) {
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(checkInTime, now).toMinutes();
    }

    public boolean isVehicleParked(String licensePlate) {
        return parkingEventRepository.hasActiveEvent(licensePlate);
    }
}