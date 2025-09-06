package com.sidar.demo2.config;

import com.sidar.demo2.model.Role;
import com.sidar.demo2.model.User;
import com.sidar.demo2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void seedDatabase() {
        createDefaultAdmin();
        createSampleUsers();
    }

    private void createDefaultAdmin() {
        if (!userRepository.existsByRole(Role.SUPER_ADMIN)) {
            User admin = User.builder()
                    .username("superadmin")
                    .email("admin@library.com")
                    .password(passwordEncoder.encode("SuperAdmin123!"))
                    .role(Role.SUPER_ADMIN)
                    .active(true)
                    .build();

            userRepository.save(admin);
            log.info("🎯 Default Super Admin created!");
            log.info("Username: superadmin");
            log.info("Password: SuperAdmin123!");
            log.info("⚠️  Please change the default password after first login!");
        }
    }

    private void createSampleUsers() {
        if (!userRepository.existsByUsername("librarian")) {
            User librarian = User.builder()
                    .username("librarian")
                    .email("librarian@library.com")
                    .password(passwordEncoder.encode("Librarian123!"))
                    .role(Role.LIBRARIAN)
                    .active(true)
                    .build();
            userRepository.save(librarian);
            log.info("📚 Sample librarian created: librarian / Librarian123!");
        }

        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin2@library.com")
                    .password(passwordEncoder.encode("Admin123!"))
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
            log.info("👤 Sample admin created: admin / Admin123!");
        }
    }
}