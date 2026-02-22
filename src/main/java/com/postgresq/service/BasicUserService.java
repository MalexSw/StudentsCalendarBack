package com.postgresq.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.postgresq.model.Calendar;
import com.postgresq.model.User;
import com.postgresq.persistence.JpaContext;

import jakarta.persistence.EntityManager;

public class BasicUserService {

    public static User createUser(String name, String surname, String email, String password, Optional<LocalDateTime> timezone) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setTimezone(timezone.orElse(LocalDateTime.now()));

        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public static void addCalendarToUser(UUID userId, Calendar calendar) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);
        if (user != null) {
            user.addCalendars(calendar);
            em.persist(calendar);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static void changePassword(UUID userId, String oldPassword, String newPassword) {
        EntityManager em = JpaContext.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);
        if (user != null && user.getPasswordHash().equals(oldPassword)) {
            user.setPasswordHash(newPassword);
            em.persist(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static List<User> listUsers() {
        EntityManager em = JpaContext.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.calendars",
                    User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
