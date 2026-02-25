package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AuthedRequest;
import com.model.User;
import com.postgresq.service.BasicSecurityTokenService;
import com.repository.UserRepository;

/**
 * Exposes a simple read/write API for managing the in-memory user list.
 */
@RestController
@RequestMapping("/users")
public class CalendarController {

    private final UserRepository userRepository;

    public CalendarController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/hormonogram")
    public String getHormonogram(@RequestBody AuthedRequest request) {
        if (!request.isValid()) {
            throw new IllegalArgumentException("Token is required");
        }
        if (!userRepository.findByEmail(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        if (!BasicSecurityTokenService.isValidToken(request.getToken(), request.getUsername())) {
            throw new IllegalArgumentException("Invalid token");
        }

        System.out.println("Received request: " + request.getToken());
        return "Hormonogram data would go here";
    }

}
