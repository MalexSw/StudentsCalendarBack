package com.postgresq.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresq.model.Calendar;

public interface PgCalendarRepository extends JpaRepository<Calendar, UUID> {

    List<Calendar> findByUserId(UUID userId);
}
