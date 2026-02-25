package com.postgresq.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.postgresq.api.dto.PgEventDtos.CreateEventRequest;
import com.postgresq.model.Calendar;
import com.postgresq.model.Event;
import com.postgresq.repository.PgCalendarRepository;
import com.postgresq.repository.PgEventRepository;

@Service
public class PgEventService {

    private final PgEventRepository eventRepository;
    private final PgCalendarRepository calendarRepository;

    public PgEventService(PgEventRepository eventRepository, PgCalendarRepository calendarRepository) {
        this.eventRepository = eventRepository;
        this.calendarRepository = calendarRepository;
    }

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    public List<Event> listEventsForCalendar(UUID calendarId) {
        return eventRepository.findByCalendarId(calendarId);
    }

    @Transactional
    public Event createEvent(CreateEventRequest request) {
        UUID calendarId = request.calendarId();
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Calendar not found: " + calendarId));

        Event event = new Event();
        event.setTitle(request.title());
        event.setGroupName(request.groupName());
        event.setStartTime(request.startTime());
        event.setEndTime(request.endTime());
        event.setLocation(request.location());
        event.setTimezone(request.timezone() != null ? request.timezone() : LocalDateTime.now());
        event.setCalendar(calendar);

        return eventRepository.save(event);
    }
}
