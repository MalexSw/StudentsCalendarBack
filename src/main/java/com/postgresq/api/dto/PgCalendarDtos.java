package com.postgresq.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public final class PgCalendarDtos {

    private PgCalendarDtos() {
    }

    public record CreateCalendarRequest(
            UUID userId,
            String title,
            LocalDateTime timezone) {

        public boolean isValid() {
            return userId != null && title != null && !title.isBlank();
        }
    }

    public record CalendarResponse(
            UUID id,
            UUID userId,
            String title,
            LocalDateTime timezone) {

    }
}
