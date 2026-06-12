package com.hi.Authservice.controller;

import com.hi.Authservice.dto.AuthResponse;
import com.hi.Authservice.dto.LoginRequest;
import com.hi.Authservice.dto.RegisterRequest;
import com.hi.Authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        log.info("POST /auth/register — registration request received: username={}, role={}",
                request.getUsername(), request.getRole());

        AuthResponse response = authService.register(request);

        log.info("POST /auth/register — registration response: username={}, message={}",
                request.getUsername(), response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("POST /auth/login — login request received: username={}", request.getUsername());

        AuthResponse response = authService.login(request);

        if (response.getToken() != null) {
            log.info("POST /auth/login — login successful: username={}", request.getUsername());
        } else {
            log.warn("POST /auth/login — login failed: username={}, reason={}", request.getUsername(), response.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        log.debug("GET /auth/validate — token validation request received");
        return ResponseEntity.ok(true);
    }
}
