package com.postgresq.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.postgresq.model.Calendar;
import com.postgresq.model.Event;
import com.postgresq.model.User;
import com.postgresq.persistence.JpaContext;

import jakarta.persistence.EntityManager;

public class BasicCalendarService {

    public static Calendar createCalendar(String title, Optional<LocalDateTime> timezone) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        Calendar calendar = new Calendar();
        calendar.setTitle(title);
        calendar.setTimezone(timezone.orElse(LocalDateTime.now()));

        em.persist(calendar);
        em.getTransaction().commit();
        em.close();
        return calendar;
    }

    public static Calendar createCalendarWithUser(String title, UUID userId, Optional<LocalDateTime> timezone) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        Calendar calendar = new Calendar();
        calendar.setTitle(title);
        calendar.setTimezone(timezone.orElse(LocalDateTime.now()));

        User user = em.find(User.class, userId);
        if (user == null) {
            em.getTransaction().rollback();
            em.close();
            throw new IllegalArgumentException("User not found for id " + userId);
        }
        user.addCalendars(calendar);
        calendar.setUser(user);
        em.persist(calendar);
        em.getTransaction().commit();
        em.close();
        return calendar;
    }

    public static void addUserForCalendar(UUID userId, UUID calendarId) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);
        Calendar calendar = em.find(Calendar.class, calendarId);
        if (user != null && calendar != null) {
            user.addCalendars(calendar);
            calendar.setUser(user);
            em.persist(user);
            em.persist(calendar);
        }
        em.getTransaction().commit();
        em.close();
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

    public static List<Calendar> listCalendars() {
        EntityManager em = JpaContext.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT c FROM Calendar c "
                            + "LEFT JOIN FETCH c.user "
                            + "LEFT JOIN FETCH c.calendarEvents",
                    Calendar.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void removeCalendar(UUID calendarId) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        Calendar calendar = em.find(Calendar.class, calendarId);
        if (calendar != null) {
            User user = calendar.getUser();
            if (user != null) {
                user.removeCalendars(calendar);
                em.persist(user);
            }
            em.remove(calendar);
        }
        em.getTransaction().commit();
        em.close();
    }
}
