package com.example.userservice.mapper;

import com.example.userservice.dto.request.CreateBatchRequest;
import com.example.userservice.dto.response.BatchResponse;
import com.example.userservice.entity.Batch;
import org.springframework.stereotype.Component;

@Component
public class BatchMapper {
    
    public Batch toEntity(CreateBatchRequest request) {
        return Batch.builder()
                .name(request.getName())
                .batchCode(request.getBatchCode())
                .description(request.getDescription())
                .academyId(request.getAcademyId())
                .courseId(request.getCourseId())
                .maxCapacity(request.getMaxCapacity())
                .currentStrength(0)
                .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                .build();
    }
    
    public BatchResponse toResponse(Batch batch) {
        return BatchResponse.builder()
                .id(batch.getId())
                .name(batch.getName())
                .batchCode(batch.getBatchCode())
                .description(batch.getDescription())
                .academyId(batch.getAcademyId())
                .courseId(batch.getCourseId())
                .maxCapacity(batch.getMaxCapacity())
                .currentStrength(batch.getCurrentStrength())
                .startDate(batch.getStartDate())
                .endDate(batch.getEndDate())
                .status(batch.getStatus())
                .createdAt(batch.getCreatedAt())
                .updatedAt(batch.getUpdatedAt())
                .build();
    }
}
