package com.sidar.demo2.repository;

import com.sidar.demo2.model.Role;
import com.sidar.demo2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // ✅ DOĞRU IMPORT!

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByRole(Role role);

    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByActiveTrue(Pageable pageable);

    long countByActiveTrue();

    long countByCreatedAtAfter(LocalDateTime date);

    List<User> findByActiveFalseAndUpdatedAtBefore(LocalDateTime cutoffDate);
}