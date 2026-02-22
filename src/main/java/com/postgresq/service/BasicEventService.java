package com.postgresq.service;

import com.postgresq.model.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.micrometer.observation.Observation;

public class BasicEventService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("calendarPU");

    public static Event createEvent(String title, Optional<String> groupName, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<String> location, Optional<LocalDateTime> timezone) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setTitle(title);
        event.setGroupName(groupName.orElse(null));
        event.setStartDate(startDate.orElse(null));
        event.setEndDate(endDate.orElse(null));
        event.setLocation(location.orElse(null));
        event.setTimezone(timezone.orElse(LocalDateTime.now()));

        em.persist(event);
        em.getTransaction().commit();
        em.close();
        return event;
    }

    public static Calendar createEventWithCalendar(UUID calendarId, String title, Optional<String> groupName, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<String> location, Optional<LocalDateTime> timezone) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setTitle(title);
        event.setGroupName(groupName.orElse(null));
        event.setStartDate(startDate.orElse(null));
        event.setEndDate(endDate.orElse(null));
        event.setLocation(location.orElse(null));
        event.setTimezone(timezone.orElse(LocalDateTime.now()));
        event.setCalendarId(calendarId);

        Calendar calendar = em.find(Calendar.class, calendarId);
        if (calendar != null) {
            calendar.addCalendarEvents(event);
            em.persist(calendar);
        }

        em.persist(event);
        em.getTransaction().commit();
        em.close();
        return event;
    }

    public static void addEventsToCalendar(UUID calendarId, List<UUID> eventsIUuids) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Calendar calendar = em.find(Calendar.class, calendarId);
        if (calendar != null) {
            for (UUID eventId : eventsIUuids) {
                Event event = em.find(Event.class, eventId);
                if (event != null) {
                    calendar.addCalendarEvents(event);
                    event.setCalendarId(calendarId);
                    em.persist(event);
                }
            }
            em.persist(calendar);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void removeEvent(UUID eventId, UUID calendarId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Event event = em.find(Event.class, eventId);
        if (event != null) {
            Calendar calendar = em.find(Calendar.class, calendarId);
            if (calendar != null) {
                calendar.removeCalendarEvents(List.of(event));
                em.persist(calendar);
            }
            em.remove(event);
        }
        em.getTransaction().commit();
        em.close();
    }
}
