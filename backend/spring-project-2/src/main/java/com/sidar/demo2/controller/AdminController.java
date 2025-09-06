package com.sidar.demo2.controller;

import com.sidar.demo2.dto.UserDto;
import com.sidar.demo2.model.Role;
import com.sidar.demo2.service.UserService;
import com.sidar.demo2.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BookService bookService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Admin test endpoint accessed by: {}, authorities: {}",
                auth.getName(), auth.getAuthorities());

        return ResponseEntity.ok(Map.of(
                "message", "Admin endpoint working!",
                "user", auth.getName(),
                "authorities", auth.getAuthorities().toString()
        ));
    }

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, String>> changeUserRole(
            @PathVariable Long userId,
            @RequestParam Role newRole) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Role change request by: {}, target user: {}, new role: {}",
                auth.getName(), userId, newRole);

        userService.changeUserRole(userId, newRole);
        return ResponseEntity.ok(Map.of(
                "message", "User role updated successfully",
                "newRole", newRole.name(),
                "changedBy", auth.getName()
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Get all users request by: {}", auth.getName());

        Page<UserDto> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/by-role")
    public ResponseEntity<Page<UserDto>> getUsersByRole(@RequestParam Role role, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Get users by role request by: {}, role: {}", auth.getName(), role);

        Page<UserDto> users = userService.getUsersByRole(role, pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{userId}/status")
    public ResponseEntity<Map<String, String>> changeUserStatus(
            @PathVariable Long userId,
            @RequestParam boolean active) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Status change request by: {}, target user: {}, new status: {}",
                auth.getName(), userId, active);

        userService.changeUserStatus(userId, active);
        return ResponseEntity.ok(Map.of(
                "message", "User status updated successfully",
                "status", active ? "ACTIVE" : "INACTIVE",
                "changedBy", auth.getName()
        ));
    }

    @GetMapping("/users/search")
    public ResponseEntity<UserDto> searchUser(@RequestParam String query) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("User search request by: {}, query: {}", auth.getName(), query);

            if (query.contains("@")) {
                return ResponseEntity.ok(userService.findByEmail(query));
            }
            return ResponseEntity.ok(userService.findByUsername(query));
        } catch (RuntimeException e) {
            log.warn("User not found for query: {}", query);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("System stats request by: {}", auth.getName());

        Map<String, Object> stats = Map.of(
                "totalUsers", userService.getTotalUserCount(),
                "activeUsers", userService.getActiveUserCount(),
                "totalBooks", bookService.getTotalBookCount(),
                "registrationsToday", userService.getTodayRegistrations(),
                "requestedBy", auth.getName()
        );

        return ResponseEntity.ok(stats);
    }

    @PutMapping("/promote-to-admin/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, String>> promoteToAdmin(@PathVariable Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Admin promotion request by: {}, target user: {}", auth.getName(), userId);

        userService.changeUserRole(userId, Role.ADMIN);
        return ResponseEntity.ok(Map.of(
                "message", "User promoted to ADMIN successfully",
                "promotedBy", auth.getName()
        ));
    }

    @PostMapping("/users/bulk-deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, String>> bulkDeactivate(@RequestBody java.util.List<Long> userIds) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Bulk deactivate request by: {}, user count: {}", auth.getName(), userIds.size());

        userService.deactivateMultipleUsers(userIds);
        return ResponseEntity.ok(Map.of(
                "message", userIds.size() + " users deactivated successfully",
                "deactivatedBy", auth.getName()
        ));
    }
}