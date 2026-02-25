package com.postgresq.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.postgresq.api.dto.PgCalendarDtos.CreateCalendarRequest;
import com.postgresq.model.Calendar;
import com.postgresq.model.User;
import com.postgresq.repository.PgCalendarRepository;
import com.postgresq.repository.PgUserRepository;

@Service
public class PgCalendarService {

    private final PgCalendarRepository calendarRepository;
    private final PgUserRepository userRepository;

    public PgCalendarService(PgCalendarRepository calendarRepository, PgUserRepository userRepository) {
        this.calendarRepository = calendarRepository;
        this.userRepository = userRepository;
    }

    public List<Calendar> listCalendars() {
        return calendarRepository.findAll();
    }

    public List<Calendar> listCalendarsForUser(UUID userId) {
        return calendarRepository.findByUserId(userId);
    }

    @Transactional
    public Calendar createCalendar(CreateCalendarRequest request) {
        UUID userId = request.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));

        Calendar calendar = new Calendar();
        calendar.setTitle(request.title());
        calendar.setTimezone(request.timezone() != null ? request.timezone() : LocalDateTime.now());
        calendar.setUser(user);

        return calendarRepository.save(calendar);
    }
}
