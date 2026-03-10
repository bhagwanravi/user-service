package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batches")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "batch_code", nullable = false, unique = true)
    private String batchCode;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "academy_id", nullable = false)
    private Long academyId;
    
    @Column(name = "course_id")
    private Long courseId;
    
    @Column(name = "max_capacity")
    private Integer maxCapacity;
    
    @Column(name = "current_strength", nullable = false)
    private Integer currentStrength;
    
    @Column(name = "start_date")
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @ManyToMany(mappedBy = "batches")
    private Set<User> students = new HashSet<>();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
