package com.postgresq.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.postgresq.model.Calendar;
import com.postgresq.model.Event;
import com.postgresq.model.User;
import com.postgresq.persistence.JpaContext;
import com.postgresq.service.BasicCalendarService;
import com.postgresq.service.BasicEventService;
import com.postgresq.service.BasicUserService;

import jakarta.persistence.EntityManager;

public final class DatabaseSeeder {

    private DatabaseSeeder() {
    }

    public static void seedAndPrint() {
        cleanDatabase();
        seedSampleData();
        printDatabaseSnapshot();
    }

    private static void cleanDatabase() {
        EntityManager em = JpaContext.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Event").executeUpdate();
            em.createQuery("DELETE FROM Calendar").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void seedSampleData() {
        LocalDateTime now = LocalDateTime.now();

        User alice = BasicUserService.createUser(
            "alice",
            "Alice",
            "Miller",
            "alice@example.com",
            "alice-strong-pass",
            Optional.of(now));
        User bob = BasicUserService.createUser(
            "bob",
            "Bob",
            "Harris",
            "bob@example.com",
            "bob-strong-pass",
            Optional.of(now));

        Calendar aliceCalendar = BasicCalendarService.createCalendarWithUser(
                "Alice Work Calendar",
                alice.getId(),
                Optional.of(now.plusDays(1)));

        Calendar bobCalendar = BasicCalendarService.createCalendarWithUser(
                "Bob Personal Calendar",
                bob.getId(),
                Optional.of(now.plusDays(2)));

        BasicEventService.createEventWithCalendar(
                aliceCalendar.getId(),
                "Daily Standup",
                Optional.of("Team Sync"),
                Optional.of(LocalDate.now().plusDays(1)),
                Optional.of(LocalDate.now().plusDays(1)),
                Optional.of("Zoom"),
                Optional.of(now.plusDays(1)));

        BasicEventService.createEventWithCalendar(
                aliceCalendar.getId(),
                "Design Review",
                Optional.of("Product"),
                Optional.of(LocalDate.now().plusDays(2)),
                Optional.of(LocalDate.now().plusDays(2)),
                Optional.of("Conference Room B"),
                Optional.of(now.plusDays(2)));

        BasicEventService.createEventWithCalendar(
                bobCalendar.getId(),
                "Family Brunch",
                Optional.of("Personal"),
                Optional.of(LocalDate.now().plusDays(3)),
                Optional.of(LocalDate.now().plusDays(3)),
                Optional.of("Sunrise Cafe"),
                Optional.of(now.plusDays(3)));
    }

    private static void printDatabaseSnapshot() {
        List<User> users = BasicUserService.listUsers();
        List<Calendar> calendars = BasicCalendarService.listCalendars();
        List<Event> events = BasicEventService.listEvents();

        System.out.println("\n--- Calendar Database Snapshot ---");
        System.out.printf("Users (%d):%n", users.size());
        users.forEach(user -> System.out.println("  " + describeUser(user)));

        System.out.printf("\nCalendars (%d):%n", calendars.size());
        calendars.forEach(calendar -> System.out.println("  " + describeCalendar(calendar)));

        System.out.printf("\nEvents (%d):%n", events.size());
        events.forEach(event -> System.out.println("  " + describeEvent(event)));
    }

    private static String describeUser(User user) {
        return String.format("%s %s (%s) id=%s, calendars=%d",
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getId(),
                user.getCalendars().size());
    }

    private static String describeCalendar(Calendar calendar) {
        String owner = calendar.getUser() != null ? calendar.getUser().getName() : "<none>";
        return String.format("%s id=%s owner=%s, timezone=%s, events=%d",
                calendar.getTitle(),
                calendar.getId(),
                owner,
                calendar.getTimezone(),
                calendar.getCalendarEvents().size());
    }

    private static String describeEvent(Event event) {
        return String.format("%s (%s) calendar=%s, when=%s to %s, location=%s",
                event.getTitle(),
                event.getGroupName(),
                event.getCalendarId(),
                event.getStartTime(),
                event.getEndTime(),
                event.getLocation());
    }
}
