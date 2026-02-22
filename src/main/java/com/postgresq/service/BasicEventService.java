package com.postgresq.service;

import com.postgresq.model.*;
import com.postgresq.persistence.JpaContext;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicEventService {

    public static Event createEvent(String title, Optional<String> groupName, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<String> location, Optional<LocalDateTime> timezone) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        Event event = new Event();
        event.setTitle(title);
        event.setGroupName(groupName.orElse(null));
        event.setStartTime(startDate.orElse(null));
        event.setEndTime(endDate.orElse(null));
        event.setLocation(location.orElse(null));
        event.setTimezone(timezone.orElse(LocalDateTime.now()));

        em.persist(event);
        em.getTransaction().commit();
        em.close();
        return event;
    }

    public static Event createEventWithCalendar(UUID calendarId, String title, Optional<String> groupName, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<String> location, Optional<LocalDateTime> timezone) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        Event event = new Event();
        event.setTitle(title);
        event.setGroupName(groupName.orElse(null));
        event.setStartTime(startDate.orElse(null));
        event.setEndTime(endDate.orElse(null));
        event.setLocation(location.orElse(null));
        event.setTimezone(timezone.orElse(LocalDateTime.now()));

        Calendar calendar = em.find(Calendar.class, calendarId);
        if (calendar == null) {
            em.getTransaction().rollback();
            em.close();
            throw new IllegalArgumentException("Calendar not found for id " + calendarId);
        }
        event.setCalendar(calendar);
        calendar.addCalendarEvents(event);

        em.persist(event);
        em.getTransaction().commit();
        em.close();
        return event;
    }

    public static void addEventsToCalendar(UUID calendarId, List<UUID> eventsIUuids) {
        EntityManager em = JpaContext.createEntityManager();
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
        EntityManager em = JpaContext.createEntityManager();
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

    public static List<Event> listEvents() {
        EntityManager em = JpaContext.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
        } finally {
            em.close();
        }
    }
}
