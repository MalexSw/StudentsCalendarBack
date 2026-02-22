package com.postgresq.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.postgresq.service.BasicService;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "calendars")
public class Calendar implements BasicService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private UUID userId;
    private String title;
    private LocalDateTime timezone;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    private List<Event> calendarEvents = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getTimezone() {
        return timezone;
    }

    public void setTimezone(LocalDateTime timezone) {
        this.timezone = timezone;
    }

    public List<Event> getCalendarEvents() {
        return new ArrayList<>(calendarEvents);
    }

    public void setCalendarEvents(List<Event> calendarEvents) {
        this.calendarEvents = calendarEvents != null ? new ArrayList<>(calendarEvents) : new ArrayList<>();
    }

    public void addCalendarEvents(Event event) {
        if (event != null && !calendarEvents.contains(event)) {
            this.calendarEvents.add(event);
        }
    }

    public void removeCalendarEvents(List<Event> events) {
        if (events != null) {
            this.calendarEvents.removeAll(events);
        }
    }

    @Override
    public String toString() {
        return "Calendar[id=" + id + ", title=" + title + ", userId=" + userId + ", timezone=" + timezone
                + ", events=" + calendarEvents.size() + "]";
    }
}
