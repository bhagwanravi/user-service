package com.example.userservice.service;

import com.example.userservice.dto.request.CreateUserRequest;
import com.example.userservice.dto.request.UpdateUserRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.entity.UserRole;
import com.example.userservice.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    UserResponse createUser(CreateUserRequest request, Long createdBy);
    
    Optional<UserResponse> getUserById(Long id);
    
    Optional<UserResponse> getUserByUsername(String username);
    
    Optional<UserResponse> getUserByEmail(String email);
    
    Page<UserResponse> getUsersByAcademy(Long academyId, Pageable pageable);
    
    Page<UserResponse> getUsersByRole(Long academyId, UserRole role, Pageable pageable);
    
    Page<UserResponse> getUsersByRoleAndStatus(Long academyId, UserRole role, UserStatus status, Pageable pageable);
    
    Page<UserResponse> searchUsers(Long academyId, String searchTerm, Pageable pageable);
    
    Page<UserResponse> searchUsersByRole(Long academyId, UserRole role, String searchTerm, Pageable pageable);
    
    UserResponse updateUser(Long id, UpdateUserRequest request, Long updatedBy);
    
    void deleteUser(Long id);
    
    void updateUserStatus(Long id, UserStatus status);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    void assignUserToBatch(Long userId, Long batchId);
    
    void removeUserFromBatch(Long userId, Long batchId);
    
    List<UserResponse> getStudentsInBatch(Long batchId);
    
    long getUserCountByRole(Long academyId, UserRole role);
    
    long getUserCountByRoleAndStatus(Long academyId, UserRole role, UserStatus status);
}
