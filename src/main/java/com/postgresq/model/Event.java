package com.postgresq.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "calendarId", insertable = false, updatable = false)
    private UUID calendarId;
    private String title;
    private String groupName;
    private LocalDate startTime;
    private LocalDate endTime;
    private String location;
    private LocalDateTime timezone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendarId", nullable = false)
    private Calendar calendar;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(UUID calendarId) {
        this.calendarId = calendarId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTimezone() {
        return timezone;
    }

    public void setTimezone(LocalDateTime timezone) {
        this.timezone = timezone;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        if (calendar != null) {
            this.calendarId = calendar.getId();
        }
    }

    @Override
    public String toString() {
        return "Event[id=" + id + ", title=" + title + ", calendarId=" + calendarId + ", start=" + startTime
                + ", end=" + endTime + ", location=" + location + "]";
    }
}
