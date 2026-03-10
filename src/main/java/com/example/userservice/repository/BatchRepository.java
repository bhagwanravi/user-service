package com.example.userservice.repository;

import com.example.userservice.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    
    Optional<Batch> findByBatchCode(String batchCode);
    
    List<Batch> findByAcademyId(Long academyId);
    
    List<Batch> findByAcademyIdAndStatus(Long academyId, String status);
    
    @Query("SELECT b FROM Batch b WHERE b.academyId = :academyId AND " +
           "b.currentStrength < b.maxCapacity")
    List<Batch> findAvailableBatches(@Param("academyId") Long academyId);
    
    @Query("SELECT COUNT(b) FROM Batch b WHERE b.academyId = :academyId")
    long countByAcademyId(@Param("academyId") Long academyId);
}
