package com.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dto.AuthRegisterRequest;
import com.dto.AuthRequest;
import com.dto.AuthResponse;
import com.model.User;
import com.postgresq.service.BasicSecurityTokenService;
import com.repository.UserRepository;
import com.security.JwtUtil;

/**
 * Encapsulates authentication logic so controllers remain thin and readable.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AuthResponse> authenticate(AuthRequest request) {
        return userRepository.findByEmail(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(this::buildSuccessResponse);
    }

    public AuthResponse register(AuthRegisterRequest request) {
        User newUser = new User();
        newUser.setEmail(request.getUsername());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        userRepository.save(newUser);
        return buildSuccessResponse(newUser, "Registration successful");
    }

    public boolean usernameTaken(String username) {
        return userRepository.existsByEmail(username);
    }

    private AuthResponse buildSuccessResponse(User user) {
        return buildSuccessResponse(user, "Authentication successful");
    }

    private AuthResponse buildSuccessResponse(User user, String message) {
        String token = jwtUtil.generateToken(user.getEmail());
        BasicSecurityTokenService.addToken(token, user.getUsername());
        return new AuthResponse(token, user.getEmail(), message);
    }
}
