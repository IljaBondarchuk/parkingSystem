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
    List<Level> findByParkingLotId(Long parkingLotId);
    Optional<Level> findByParkingLotIdAndLevelNumber(Long parkingLotId, Integer levelNumber);
    boolean existsByParkingLotIdAndLevelNumber(Long parkingLotId, Integer levelNumber);
    List<Level> findByParkingLotIdOrderByLevelNumberAsc(Long parkingLotId);
}
