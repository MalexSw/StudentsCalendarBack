package com.postgresq.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresq.model.User;

public interface PgUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
