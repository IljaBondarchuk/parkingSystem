package com.example.parkingSystem.repository;

import com.example.parkingSystem.domain.entities.ParkingSpot;
import com.example.parkingSystem.domain.enums.ParkingSpotStatus;
import com.example.parkingSystem.domain.enums.ParkingSpotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByLevelId(Long levelId);

    @Query("SELECT ps FROM ParkingSpot ps " +
            "WHERE ps.level.parkingLot.Id = :parkingLotId " +
            "AND ps.status = 'AVAILABLE'")
    List<ParkingSpot> findAvailableSpotsByParkingLotId(@Param("parkingLotId") Long parkingLotId);
    @Query("SELECT COUNT(ps) FROM ParkingSpot ps " +
            "WHERE ps.level.parkingLot.Id = :parkingLotId " +
            "AND ps.status = 'AVAILABLE'")
    long countAvailableSpots(@Param("parkingLotId") Long parkingLotId);
    @Query("SELECT ps FROM ParkingSpot ps " +
            "WHERE ps.level.parkingLot.Id = :parkingLotId " +
            "AND ps.parkingSpotType = :spotType " +
            "AND ps.status = 'AVAILABLE' " +
            "ORDER BY ps.level.levelNumber ASC, ps.parkingSpotNumber ASC " +
            "LIMIT 1")
    Optional<ParkingSpot> findFirstAvailableSpotByType(
            @Param("parkingLotId") Long parkingLotId,
            @Param("spotType") ParkingSpotType spotType
    );
}
