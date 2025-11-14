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
    List<ParkingSpot> findByLevelID(Long levelId);
    Optional<ParkingSpot> findByLevelIDAndParkingSpotNumber(Long levelId, Integer parkingSpotNumber);
    List<ParkingSpot> findByLevelIDAndStatus(Long levelId, ParkingSpotStatus status);
    List<ParkingSpot> findByLevelIDAndParkingSpotTypeAndStatus(
            Long levelId,
            ParkingSpotType parkingSpotType,
            ParkingSpotStatus status
    );
    boolean existsByLevelIDAndParkingSpotNumber(Long levelId, Integer parkingSpotNumber);

    @Query("SELECT ps FROM ParkingSpot ps " +
            "WHERE ps.level.parkingLot.ID = :parkingLotId " +
            "AND ps.status = 'AVAILABLE'")
    List<ParkingSpot> findAvailableSpotsByParkingLotId(@Param("parkingLotId") Long parkingLotId);
    @Query("SELECT ps FROM ParkingSpot ps " +
            "WHERE ps.level.parkingLot.ID = :parkingLotId " +
            "AND ps.parkingSpotType = :spotType " +
            "AND ps.status = 'AVAILABLE' " +
            "ORDER BY ps.level.levelNumber ASC, ps.parkingSpotNumber ASC")
    List<ParkingSpot> findAvailableSpotsByTypeInParkingLot(
            @Param("parkingLotId") Long parkingLotId,
            @Param("spotType") ParkingSpotType spotType
    );
    @Query("SELECT ps FROM ParkingSpot ps WHERE ps.level.parkingLot.ID = :parkingLotId")
    List<ParkingSpot> findByParkingLotId(@Param("parkingLotId") Long parkingLotId);
    @Query("SELECT COUNT(ps) FROM ParkingSpot ps " +
            "WHERE ps.level.parkingLot.ID = :parkingLotId " +
            "AND ps.status = 'AVAILABLE'")
    long countAvailableSpots(@Param("parkingLotId") Long parkingLotId);
    @Query("SELECT COUNT(ps) FROM ParkingSpot ps " +
            "WHERE ps.level.parkingLot.ID = :parkingLotId " +
            "AND ps.parkingSpotType = :spotType " +
            "AND ps.status = 'AVAILABLE'")
    long countAvailableSpotsByType(
            @Param("parkingLotId") Long parkingLotId,
            @Param("spotType") ParkingSpotType spotType
    );
}
