package com.example.userservice.service.impl;

import com.example.userservice.dto.request.CreateUserRequest;
import com.example.userservice.dto.request.UpdateUserRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entity.Batch;
import com.example.userservice.entity.User;
import com.example.userservice.entity.UserRole;
import com.example.userservice.entity.UserStatus;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.BatchRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final BatchRepository batchRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponse createUser(CreateUserRequest request, Long createdBy) {
        log.info("Creating user with username: {}", request.getUsername());
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedBy(createdBy);
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        
        return userMapper.toResponse(savedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsersByAcademy(Long academyId, Pageable pageable) {
        return userRepository.findAllByAcademyId(academyId, pageable)
                .map(userMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsersByRole(Long academyId, UserRole role, Pageable pageable) {
        return userRepository.findByAcademyIdAndRole(academyId, role, pageable)
                .map(userMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsersByRoleAndStatus(Long academyId, UserRole role, UserStatus status, Pageable pageable) {
        return userRepository.findByAcademyIdAndRoleAndStatus(academyId, role, status, pageable)
                .map(userMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(Long academyId, String searchTerm, Pageable pageable) {
        return userRepository.searchUsers(academyId, searchTerm, pageable)
                .map(userMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsersByRole(Long academyId, UserRole role, String searchTerm, Pageable pageable) {
        return userRepository.searchUsersByRole(academyId, role, searchTerm, pageable)
                .map(userMapper::toResponse);
    }
    
    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request, Long updatedBy) {
        log.info("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        userMapper.updateEntity(user, request);
        user.setUpdatedBy(updatedBy);
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        
        return userMapper.toResponse(updatedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
    
    @Override
    public void updateUserStatus(Long id, UserStatus status) {
        log.info("Updating user status to {} for user ID: {}", status, id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        user.setStatus(status);
        userRepository.save(user);
        log.info("User status updated successfully for user ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public void assignUserToBatch(Long userId, Long batchId) {
        log.info("Assigning user ID: {} to batch ID: {}", userId, batchId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found with ID: " + batchId));
        
        if (batch.getCurrentStrength() >= batch.getMaxCapacity()) {
            throw new RuntimeException("Batch is already at full capacity");
        }
        
        user.getBatches().add(batch);
        batch.setCurrentStrength(batch.getCurrentStrength() + 1);
        
        userRepository.save(user);
        batchRepository.save(batch);
        
        log.info("User ID: {} assigned to batch ID: {} successfully", userId, batchId);
    }
    
    @Override
    public void removeUserFromBatch(Long userId, Long batchId) {
        log.info("Removing user ID: {} from batch ID: {}", userId, batchId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found with ID: " + batchId));
        
        if (user.getBatches().remove(batch)) {
            batch.setCurrentStrength(Math.max(0, batch.getCurrentStrength() - 1));
            userRepository.save(user);
            batchRepository.save(batch);
            log.info("User ID: {} removed from batch ID: {} successfully", userId, batchId);
        } else {
            log.warn("User ID: {} was not in batch ID: {}", userId, batchId);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getStudentsInBatch(Long batchId) {
        return userRepository.findByBatchesId(batchId).stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getUserCountByRole(Long academyId, UserRole role) {
        return userRepository.countByAcademyIdAndRole(academyId, role);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getUserCountByRoleAndStatus(Long academyId, UserRole role, UserStatus status) {
        return userRepository.countByAcademyIdAndRoleAndStatus(academyId, role, status);
    }
}
