package com.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.model.User;

import jakarta.annotation.PostConstruct;

/**
 * Very simple in-memory storage that keeps a list of users for the demo.
 */
@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger();
    private final PasswordEncoder passwordEncoder;

    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    void loadSampleUsers() {
        String hashed = passwordEncoder.encode("password123");
        users.add(new User(idGenerator.incrementAndGet(), "Prem", "Tiwari", "prem@gmail.com", hashed));
        users.add(new User(idGenerator.incrementAndGet(), "Vikash", "Kumar", "vikash@gmail.com", hashed));
        users.add(new User(idGenerator.incrementAndGet(), "Ritesh", "Ojha", "ritesh@gmail.com", hashed));
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.incrementAndGet());
        } else {
            idGenerator.updateAndGet(current -> Math.max(current, user.getId()));
        }
        users.add(user);
        return user;
    }
}
