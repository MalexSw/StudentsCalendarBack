package com.postgresq.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public final class PgUserDtos {

    private PgUserDtos() {
    }

    public record CreatePgUserRequest(
            String username,
            String name,
            String surname,
            String email,
            String password,
            LocalDateTime timezone) {

        public boolean isValid() {
            return notBlank(username) && notBlank(name) && notBlank(surname) && notBlank(email) && notBlank(password);
        }

        private static boolean notBlank(String value) {
            return value != null && !value.isBlank();
        }
    }

    public record PgUserResponse(
            UUID id,
            String username,
            String name,
            String surname,
            String email,
            LocalDateTime timezone) {

    }
}
