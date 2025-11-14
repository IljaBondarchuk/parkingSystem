package com.example.parkingSystem.repository;

import com.example.parkingSystem.domain.entities.ParkingEvent;
import com.example.parkingSystem.domain.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.parkingSystem.domain.entities.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingEventRepository extends JpaRepository<ParkingEvent, Long> {
    Optional<ParkingEvent> findByTicketNumber(String ticketNumber);
    List<ParkingEvent> findByIsActiveTrue();
    Optional<ParkingEvent> findByVehicleAndIsActiveTrue(Vehicle vehicle);
    List<ParkingEvent> findByVehicleOrderByCheckInTimeDesc(Vehicle vehicle);
    @Query("SELECT pe FROM ParkingEvent pe " +
            "WHERE pe.vehicle.licensePlate = :licensePlate " +
            "AND pe.isActive = true")
    Optional<ParkingEvent> findActiveEventByLicensePlate(@Param("licensePlate") String licensePlate);
    @Query("SELECT pe FROM ParkingEvent pe " +
            "WHERE pe.parkingSpot.level.parkingLot.ID = :parkingLotId " +
            "AND pe.isActive = true")
    List<ParkingEvent> findActiveEventsByParkingLotId(@Param("parkingLotId") Long parkingLotId);
    @Query("SELECT COUNT(pe) FROM ParkingEvent pe " +
            "WHERE pe.parkingSpot.level.parkingLot.ID = :parkingLotId " +
            "AND pe.isActive = true")
    long countActiveEventsByParkingLotId(@Param("parkingLotId") Long parkingLotId);
    @Query("SELECT pe FROM ParkingEvent pe " +
            "WHERE pe.parkingSpot.ID = :spotId " +
            "AND pe.isActive = true")
    Optional<ParkingEvent> findActiveEventBySpotId(@Param("spotId") Long spotId);
    @Query("SELECT COUNT(pe) > 0 FROM ParkingEvent pe " +
            "WHERE pe.vehicle.licensePlate = :licensePlate " +
            "AND pe.isActive = true")
    boolean hasActiveEvent(@Param("licensePlate") String licensePlate);


}
