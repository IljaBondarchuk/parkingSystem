package com.example.parkingSystem.repository;

import com.example.parkingSystem.domain.entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findByParkingLotID(Long parkingLotId);
    Optional<Level> findByParkingLotIDAndLevelNumber(Long parkingLotId, Integer levelNumber);
    boolean existsByParkingLotIDAndLevelNumber(Long parkingLotId, Integer levelNumber);
    @Query("SELECT l FROM Level l " +
            "LEFT JOIN FETCH l.parkingSpotsList " +
            "WHERE l.ID = :id")
    Optional<Level> findByIdWithSpots(@Param("id") Long id);
    @Query("SELECT DISTINCT l FROM Level l " +
            "JOIN l.parkingSpotsList ps " +
            "WHERE l.parkingLot.ID = :parkingLotId " +
            "AND ps.status = 'AVAILABLE'")
    List<Level> findLevelsWithAvailableSpots(@Param("parkingLotId") Long parkingLotId);
}
