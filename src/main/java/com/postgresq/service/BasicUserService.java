package com.postgresq.service;

import com.postgresq.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BasicUserService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("calendarPU");

    public static User createUser(String username, String email, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(username);
        user.setEmail(email);
        user.setPasswordHash(password);

        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public static void addCalendarToUser(Long userId, Calendar calendar) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);
        if (user != null) {
            user.addCalendars(calendar);
            em.persist(calendar);
        }
        em.getTransaction().commit();
        em.close();
    }
}
