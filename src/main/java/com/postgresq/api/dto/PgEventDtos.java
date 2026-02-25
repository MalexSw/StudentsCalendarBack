package com.postgresq.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public final class PgEventDtos {

    private PgEventDtos() {
    }

    public record CreateEventRequest(
            UUID calendarId,
            String title,
            String groupName,
            LocalDate startTime,
            LocalDate endTime,
            String location,
            LocalDateTime timezone) {

        public boolean isValid() {
            return calendarId != null && title != null && !title.isBlank();
        }
    }

    public record EventResponse(
            UUID id,
            UUID calendarId,
            String title,
            String groupName,
            LocalDate startTime,
            LocalDate endTime,
            String location,
            LocalDateTime timezone) {

    }
}
