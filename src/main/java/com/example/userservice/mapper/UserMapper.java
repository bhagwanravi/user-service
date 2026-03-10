package com.example.userservice.mapper;

import com.example.userservice.dto.request.CreateUserRequest;
import com.example.userservice.dto.request.UpdateUserRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.entity.UserStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .status(UserStatus.ACTIVE)
                .academyId(request.getAcademyId())
                .profileImageUrl(request.getProfileImageUrl())
                .address(request.getAddress())
                .emergencyContact(request.getEmergencyContact())
                .emergencyPhone(request.getEmergencyPhone())
                .build();
    }
    
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .status(user.getStatus())
                .academyId(user.getAcademyId())
                .profileImageUrl(user.getProfileImageUrl())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .emergencyContact(user.getEmergencyContact())
                .emergencyPhone(user.getEmergencyPhone())
                .batches(user.getBatches().stream()
                        .map(batch -> com.example.userservice.dto.response.BatchResponse.builder()
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
                                .build())
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    
    public void updateEntity(User user, UpdateUserRequest request) {
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getProfileImageUrl() != null) {
            user.setProfileImageUrl(request.getProfileImageUrl());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getEmergencyContact() != null) {
            user.setEmergencyContact(request.getEmergencyContact());
        }
        if (request.getEmergencyPhone() != null) {
            user.setEmergencyPhone(request.getEmergencyPhone());
        }
    }
}
