package com.postgresq.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaContext {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("calendarPU");

    private JpaContext() {
    }

    public static EntityManager createEntityManager() {
        return EMF.createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }
}
