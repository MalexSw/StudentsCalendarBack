package com.postgresq.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresq.model.Event;

public interface PgEventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByCalendarId(UUID calendarId);
}
