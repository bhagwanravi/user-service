package com.example.userservice.repository;

import com.example.userservice.entity.User;
import com.example.userservice.entity.UserRole;
import com.example.userservice.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    Page<User> findByAcademyIdAndRole(Long academyId, UserRole role, Pageable pageable);
    
    Page<User> findByAcademyIdAndRoleAndStatus(Long academyId, UserRole role, UserStatus status, Pageable pageable);
    
    List<User> findByAcademyIdAndRole(Long academyId, UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.academyId = :academyId AND " +
           "(LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<User> searchUsers(@Param("academyId") Long academyId, 
                           @Param("searchTerm") String searchTerm, 
                           Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.academyId = :academyId AND u.role = :role AND " +
           "(LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<User> searchUsersByRole(@Param("academyId") Long academyId, 
                                @Param("role") UserRole role,
                                @Param("searchTerm") String searchTerm, 
                                Pageable pageable);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.academyId = :academyId AND u.role = :role")
    long countByAcademyIdAndRole(@Param("academyId") Long academyId, @Param("role") UserRole role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.academyId = :academyId AND u.role = :role AND u.status = :status")
    long countByAcademyIdAndRoleAndStatus(@Param("academyId") Long academyId, 
                                        @Param("role") UserRole role, 
                                        @Param("status") UserStatus status);
}
