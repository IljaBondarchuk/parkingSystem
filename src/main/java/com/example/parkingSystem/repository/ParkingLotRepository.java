package com.example.parkingSystem.repository;

import com.example.parkingSystem.domain.entities.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {
    Optional<ParkingLot> findByName(String name);
    boolean existsByName(String name);
    List<ParkingLot> findByIsActiveTrue();
    @Query("SELECT DISTINCT pl FROM ParkingLot pl " +
            "JOIN pl.levelsList l " +
            "JOIN l.parkingSpotsList ps " +
            "WHERE ps.status = 'AVAILABLE'")
    List<ParkingLot> findParkingLotsWithAvalibleSpots();


}
