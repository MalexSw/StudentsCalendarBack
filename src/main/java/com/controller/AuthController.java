package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AuthRequest;
import com.dto.AuthRegisterRequest;
import com.dto.AuthResponse;
import com.service.AuthService;

/**
 * REST endpoints responsible for authentication and registration.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        if (!authRequest.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username and password are required");
        }
        var authResponse = authService.authenticate(authRequest);
        if (authResponse.isPresent()) {
            return ResponseEntity.ok(authResponse.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegisterRequest authRequest) {
        if (!authRequest.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username, password, first name, and last name are required");
        }
        if (authService.usernameTaken(authRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        AuthResponse response = authService.register(authRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
