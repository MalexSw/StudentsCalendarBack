package com.postgresq.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.postgresq.api.dto.PgCalendarDtos.CalendarResponse;
import com.postgresq.api.dto.PgCalendarDtos.CreateCalendarRequest;
import com.postgresq.model.Calendar;
import com.postgresq.service.PgCalendarService;

@RestController
@RequestMapping("/pg/calendars")
public class PgCalendarsController {

    private final PgCalendarService calendarService;

    public PgCalendarsController(PgCalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public List<CalendarResponse> list(@RequestParam(name = "userId", required = false) UUID userId) {
        List<Calendar> calendars = userId == null
                ? calendarService.listCalendars()
                : calendarService.listCalendarsForUser(userId);
        return calendars.stream().map(PgCalendarsController::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CalendarResponse create(@RequestBody CreateCalendarRequest request) {
        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("userId and title are required");
        }
        return toResponse(calendarService.createCalendar(request));
    }

    private static CalendarResponse toResponse(Calendar calendar) {
        UUID userId = calendar.getUserId();
        if (userId == null && calendar.getUser() != null) {
            userId = calendar.getUser().getId();
        }
        return new CalendarResponse(
                calendar.getId(),
                userId,
                calendar.getTitle(),
                calendar.getTimezone());
    }
}
