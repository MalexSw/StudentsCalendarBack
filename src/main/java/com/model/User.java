package com.model;

/**
 * Plain Java object that represents a user registered in the demo application.
 */
public class User implements Comparable<User> {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String calendarURL;

    public User() {
    }

    public User(Integer id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCalendarURL() {
        return calendarURL;
    }

    public void setCalendarURL(String calendarURL) {
        this.calendarURL = calendarURL;
    }

    @Override
    public int compareTo(User other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + ", password=" + password + ", calendarURL=" + calendarURL + "]";
    }
}
