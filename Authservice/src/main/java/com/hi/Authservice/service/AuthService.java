package com.hi.Authservice.service;

import com.hi.Authservice.dto.AuthResponse;
import com.hi.Authservice.dto.LoginRequest;
import com.hi.Authservice.dto.RegisterRequest;
import com.hi.Authservice.entity.UserEntity;
import com.hi.Authservice.repository.UserRepository;
import com.hi.Authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        log.info("Registration attempt: username={}, role={}", request.getUsername(), request.getRole());

        Optional<UserEntity> existing = userRepository.findByUsername(request.getUsername());
        if (existing.isPresent()) {
            log.warn("Registration failed — username already exists: username={}", request.getUsername());
            return new AuthResponse(null, "Username already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        log.info("User registered successfully: username={}, role={}", request.getUsername(), request.getRole());

        return new AuthResponse(null, "User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt: username={}", request.getUsername());

        Optional<UserEntity> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            log.warn("Login failed — user not found: username={}", request.getUsername());
            return new AuthResponse(null, "User not found");
        }

        UserEntity user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed — invalid password: username={}", request.getUsername());
            return new AuthResponse(null, "Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        log.info("Login successful — JWT issued: username={}, role={}", user.getUsername(), user.getRole());

        return new AuthResponse(token, "Login successful");
    }
}
