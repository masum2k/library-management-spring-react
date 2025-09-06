package com.sidar.demo2.controller;

import com.sidar.demo2.auth.JwtUtil;
import com.sidar.demo2.dto.AuthRequest;
import com.sidar.demo2.dto.AuthResponse;
import com.sidar.demo2.dto.LoginRequest;
import com.sidar.demo2.dto.RegisterRequest;
import com.sidar.demo2.model.Role;
import com.sidar.demo2.model.User;
import com.sidar.demo2.repository.UserRepository;
import com.sidar.demo2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(AuthResponse.builder().error(e.getMessage()).build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(AuthResponse.builder().error("Invalid credentials").build());
        }
    }
}