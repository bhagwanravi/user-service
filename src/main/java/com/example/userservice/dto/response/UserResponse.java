package com.example.userservice.dto.response;

import com.example.userservice.entity.UserRole;
import com.example.userservice.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole role;
    private UserStatus status;
    private Long academyId;
    private String profileImageUrl;
    private LocalDateTime dateOfBirth;
    private String address;
    private String emergencyContact;
    private String emergencyPhone;
    private Set<BatchResponse> batches;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
