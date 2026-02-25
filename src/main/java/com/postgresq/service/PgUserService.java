package com.postgresq.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.postgresq.api.dto.PgUserDtos.CreatePgUserRequest;
import com.postgresq.model.User;
import com.postgresq.repository.PgUserRepository;

@Service
public class PgUserService {

    private final PgUserRepository userRepository;

    public PgUserService(PgUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(CreatePgUserRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setName(request.name());
        user.setSurname(request.surname());
        user.setEmail(request.email());
        user.setPasswordHash(request.password());
        user.setTimezone(request.timezone() != null ? request.timezone() : LocalDateTime.now());
        return userRepository.save(user);
    }
}
