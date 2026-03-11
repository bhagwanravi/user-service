package com.example.userservice.controller;

import com.example.userservice.dto.request.CreateUserRequest;
import com.example.userservice.dto.request.UpdateUserRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entity.UserRole;
import com.example.userservice.entity.UserStatus;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Creating new user with username: {}", request.getUsername());
        UserResponse response = userService.createUser(request, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/academy/{academyId}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<Page<UserResponse>> getUsersByAcademy(
            @PathVariable Long academyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<UserResponse> users = userService.getUsersByAcademy(academyId, pageable);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/academy/{academyId}/role/{role}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<Page<UserResponse>> getUsersByRole(
            @PathVariable Long academyId,
            @PathVariable UserRole role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<UserResponse> users = userService.getUsersByRole(academyId, role, pageable);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/academy/{academyId}/role/{role}/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<Page<UserResponse>> getUsersByRoleAndStatus(
            @PathVariable Long academyId,
            @PathVariable UserRole role,
            @PathVariable UserStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<UserResponse> users = userService.getUsersByRoleAndStatus(academyId, role, status, pageable);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<Page<UserResponse>> searchUsers(
            @RequestParam Long academyId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<UserResponse> users = userService.searchUsers(academyId, searchTerm, pageable);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/search/role")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<Page<UserResponse>> searchUsersByRole(
            @RequestParam Long academyId,
            @RequestParam UserRole role,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<UserResponse> users = userService.searchUsersByRole(academyId, role, searchTerm, pageable);
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_UPDATE') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUser(id, request, null);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_UPDATE')")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status) {
        userService.updateUserStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{userId}/batch/{batchId}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_UPDATE')")
    public ResponseEntity<Void> assignUserToBatch(
            @PathVariable Long userId,
            @PathVariable Long batchId) {
        userService.assignUserToBatch(userId, batchId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{userId}/batch/{batchId}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_UPDATE')")
    public ResponseEntity<Void> removeUserFromBatch(
            @PathVariable Long userId,
            @PathVariable Long batchId) {
        userService.removeUserFromBatch(userId, batchId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/batch/{batchId}/students")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<List<UserResponse>> getStudentsInBatch(@PathVariable Long batchId) {
        List<UserResponse> students = userService.getStudentsInBatch(batchId);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/academy/{academyId}/role/{role}/count")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<Long> getUserCountByRole(
            @PathVariable Long academyId,
            @PathVariable UserRole role) {
        long count = userService.getUserCountByRole(academyId, role);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/academy/{academyId}/role/{role}/status/{status}/count")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_READ')")
    public ResponseEntity<Long> getUserCountByRoleAndStatus(
            @PathVariable Long academyId,
            @PathVariable UserRole role,
            @PathVariable UserStatus status) {
        long count = userService.getUserCountByRoleAndStatus(academyId, role, status);
        return ResponseEntity.ok(count);
    }
}
