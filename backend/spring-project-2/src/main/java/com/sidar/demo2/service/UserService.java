// src/main/java/com/sidar/demo2/service/UserService.java
package com.sidar.demo2.service;

import com.sidar.demo2.dto.UserDto;
import com.sidar.demo2.model.Role;
import com.sidar.demo2.model.User;
import com.sidar.demo2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional
    public void changeUserRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Role oldRole = user.getRole();
        user.setRole(newRole);
        userRepository.save(user);

        log.info("User {} role changed from {} to {} by {}",
                user.getUsername(), oldRole, newRole, getCurrentUsername());
    }

    @Transactional
    public void changeUserStatus(Long userId, boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setActive(active);
        userRepository.save(user);

        log.info("User {} status changed to {} by {}",
                user.getUsername(), active ? "ACTIVE" : "INACTIVE", getCurrentUsername());
    }

    public long getTotalUserCount() {
        return userRepository.count();
    }

    public long getActiveUserCount() {
        return userRepository.countByActiveTrue();
    }

    public long getTodayRegistrations() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        return userRepository.countByCreatedAtAfter(startOfDay);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return convertToDto(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToDto(user);
    }

    public Page<UserDto> getUsersByRole(Role role, Pageable pageable) {
        return userRepository.findByRole(role, pageable)
                .map(this::convertToDto);
    }

    public Page<UserDto> getActiveUsers(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable)
                .map(this::convertToDto);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .build();
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "system";
    }

    @Transactional
    public void deactivateMultipleUsers(java.util.List<Long> userIds) {
        userRepository.findAllById(userIds).forEach(user -> {
            user.setActive(false);
            userRepository.save(user);
        });

        log.info("Deactivated {} users by {}", userIds.size(), getCurrentUsername());
    }
}