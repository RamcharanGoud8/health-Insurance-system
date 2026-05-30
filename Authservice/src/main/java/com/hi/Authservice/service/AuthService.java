package com.hi.Authservice.service;

import com.hi.Authservice.dto.AuthResponse;
import com.hi.Authservice.dto.LoginRequest;
import com.hi.Authservice.dto.RegisterRequest;
import com.hi.Authservice.entity.UserEntity;
import com.hi.Authservice.repository.UserRepository;
import com.hi.Authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        // Check if username already exists
        Optional<UserEntity> existing = userRepository.findByUsername(request.getUsername());
        if (existing.isPresent()) {
            return new AuthResponse(null, "Username already exists");
        }

        // Save user with encoded password
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return new AuthResponse(null, "User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {

        // Find user
        Optional<UserEntity> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            return new AuthResponse(null, "User not found");
        }

        UserEntity user = userOpt.get();

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(null, "Invalid password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponse(token, "Login successful");
    }
}