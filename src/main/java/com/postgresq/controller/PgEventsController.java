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

import com.postgresq.api.dto.PgEventDtos.CreateEventRequest;
import com.postgresq.api.dto.PgEventDtos.EventResponse;
import com.postgresq.model.Event;
import com.postgresq.service.PgEventService;

@RestController
@RequestMapping("/pg/events")
public class PgEventsController {

    private final PgEventService eventService;

    public PgEventsController(PgEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponse> list(@RequestParam(name = "calendarId", required = false) UUID calendarId) {
        List<Event> events = calendarId == null
                ? eventService.listEvents()
                : eventService.listEventsForCalendar(calendarId);
        return events.stream().map(PgEventsController::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse create(@RequestBody CreateEventRequest request) {
        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("calendarId and title are required");
        }
        return toResponse(eventService.createEvent(request));
    }

    private static EventResponse toResponse(Event event) {
        UUID calendarId = event.getCalendarId();
        if (calendarId == null && event.getCalendar() != null) {
            calendarId = event.getCalendar().getId();
        }
        return new EventResponse(
                event.getId(),
                calendarId,
                event.getTitle(),
                event.getGroupName(),
                event.getStartTime(),
                event.getEndTime(),
                event.getLocation(),
                event.getTimezone());
    }
}
