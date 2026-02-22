package calendarBack.src.main.java.com.postgresq.model;

import java.time.LocalDate;
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
    private LocalDate timezone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Calendar> calendars;

    // Getters and setters
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

    public LocalDate getTimezone() {
        return timezone;
    }

    public void setTimezone(LocalDate timezone) {
        this.timezone = timezone;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public List<Calendar> addCalendars(Calendar calendar) {
        this.calendars.add(calendar);
        return this.calendars;
    }

    public List<Calendar> removeCalendars(Calendar calendar) {
        this.calendars.remove(calendar);
        return this.calendars;
    }

    public void removeAllCalendars() {
        this.calendars.clear();
    }
}
