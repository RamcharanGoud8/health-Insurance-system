package com.hi.Authservice.controller;


import com.hi.Authservice.dto.AuthResponse;
import com.hi.Authservice.dto.LoginRequest;
import com.hi.Authservice.dto.RegisterRequest;
import com.hi.Authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {

        return new ResponseEntity<>(
                authService.register(request),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        return new ResponseEntity<>(
                authService.login(request),
                HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(
            @RequestParam String token) {

        return ResponseEntity.ok(true);
    }
}
