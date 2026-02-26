package com.postgresq.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.postgresq.model.Calendar;
import com.postgresq.model.Event;
import com.postgresq.model.User;
import com.postgresq.repository.PgCalendarRepository;
import com.postgresq.repository.PgEventRepository;
import com.postgresq.repository.PgUserRepository;

@Service
public class DatabaseSeeder {

    private final PgUserRepository userRepository;
    private final PgCalendarRepository calendarRepository;
    private final PgEventRepository eventRepository;

    public DatabaseSeeder(PgUserRepository userRepository, PgCalendarRepository calendarRepository, PgEventRepository eventRepository) {
        this.userRepository = userRepository;
        this.calendarRepository = calendarRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void seedAndPrint() {
        cleanDatabase();
        seedSampleData();
        printDatabaseSnapshot();
    }

    private void cleanDatabase() {
        // delete in FK-safe order
        eventRepository.deleteAll();
        calendarRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void seedSampleData() {
        LocalDateTime now = LocalDateTime.now();

        User alice = new User();
        alice.setUsername("alice");
        alice.setName("Alice");
        alice.setSurname("Miller");
        alice.setEmail("alice@example.com");
        alice.setPasswordHash("alice-strong-pass");
        alice.setTimezone(now);
        alice = userRepository.save(alice);

        User bob = new User();
        bob.setUsername("bob");
        bob.setName("Bob");
        bob.setSurname("Harris");
        bob.setEmail("bob@example.com");
        bob.setPasswordHash("bob-strong-pass");
        bob.setTimezone(now);
        bob = userRepository.save(bob);

        Calendar aliceCalendar = new Calendar();
        aliceCalendar.setTitle("Alice Work Calendar");
        aliceCalendar.setTimezone(now.plusDays(1));
        aliceCalendar.setUser(alice);
        aliceCalendar = calendarRepository.save(aliceCalendar);

        Calendar bobCalendar = new Calendar();
        bobCalendar.setTitle("Bob Personal Calendar");
        bobCalendar.setTimezone(now.plusDays(2));
        bobCalendar.setUser(bob);
        bobCalendar = calendarRepository.save(bobCalendar);

        Event standup = new Event();
        standup.setTitle("Daily Standup");
        standup.setGroupName("Team Sync");
        standup.setStartTime(LocalDate.now().plusDays(1));
        standup.setEndTime(LocalDate.now().plusDays(1));
        standup.setLocation("Zoom");
        standup.setTimezone(now.plusDays(1));
        standup.setCalendar(aliceCalendar);
        eventRepository.save(standup);

        Event review = new Event();
        review.setTitle("Design Review");
        review.setGroupName("Product");
        review.setStartTime(LocalDate.now().plusDays(2));
        review.setEndTime(LocalDate.now().plusDays(2));
        review.setLocation("Conference Room B");
        review.setTimezone(now.plusDays(2));
        review.setCalendar(aliceCalendar);
        eventRepository.save(review);

        Event brunch = new Event();
        brunch.setTitle("Family Brunch");
        brunch.setGroupName("Personal");
        brunch.setStartTime(LocalDate.now().plusDays(3));
        brunch.setEndTime(LocalDate.now().plusDays(3));
        brunch.setLocation("Sunrise Cafe");
        brunch.setTimezone(now.plusDays(3));
        brunch.setCalendar(bobCalendar);
        eventRepository.save(brunch);
    }

    private void printDatabaseSnapshot() {
        List<User> users = userRepository.findAll();
        List<Calendar> calendars = calendarRepository.findAll();
        List<Event> events = eventRepository.findAll();

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
