package com.postgresq.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String passwordHash;
    private LocalDateTime timezone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Calendar> calendars = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getTimezone() {
        return timezone;
    }

    public void setTimezone(LocalDateTime timezone) {
        this.timezone = timezone;
    }

    public List<Calendar> getCalendars() {
        return new ArrayList<>(calendars);
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars != null ? new ArrayList<>(calendars) : new ArrayList<>();
    }

    public List<Calendar> addCalendars(Calendar calendar) {
        if (calendar != null && !calendars.contains(calendar)) {
            this.calendars.add(calendar);
        }
        return calendars;
    }

    public List<Calendar> removeCalendars(Calendar calendar) {
        if (calendar != null) {
            this.calendars.remove(calendar);
        }
        return calendars;
    }

    public void removeAllCalendars() {
        this.calendars.clear();
    }

    @Override
    public String toString() {
        return "User[id=" + id + ", name=" + name + " " + surname + ", email=" + email
                + ", timezone=" + timezone + ", calendars=" + calendars.size() + "]";
    }
}
