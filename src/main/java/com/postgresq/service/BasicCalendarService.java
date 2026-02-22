package com.postgresq.service;

import com.postgresq.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BasicCalendarService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("calendarPU");

    public static Calendar createCalendar(String title, Optional<LocalDateTime> timezone) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Calendar calendar = new Calendar();
        calendar.setId(UUID.randomUUID());
        calendar.setTitle(title);
        calendar.setTimezone(timezone.orElse(LocalDateTime.now()));

        em.persist(calendar);
        em.getTransaction().commit();
        em.close();
        return calendar;
    }

    public static Calendar createCalendarWithUser(String title, UUID userId, Optional<LocalDateTime> timezone) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Calendar calendar = new Calendar();
        calendar.setId(UUID.randomUUID());
        calendar.setUserId(userId);
        calendar.setTitle(title);
        calendar.setTimezone(timezone.orElse(LocalDateTime.now()));

        em.persist(calendar);
        em.getTransaction().commit();
        em.close();
        return calendar;
    }

    public static void addUserForCalendar(UUID userId, UUID calendarId) {
        EntityManager em = emf.createEntityManager();
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

    public void removeCalendar(UUID calendarId) {
        EntityManager em = emf.createEntityManager();
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
