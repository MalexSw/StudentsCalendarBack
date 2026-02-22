package com.model;

import java.util.Date;

/**
 * Represents a calendar entry decoded from the iCal feed. Maintains a mix of
 * metadata used by the (future) parser.
 */
public class Subject {

    private long id;
    private String name;
    private Date date;
    private String summary;
    private String start;
    private String end;
    private String roomNumber;
    private String building;
    private String location;
    private String shortDescription;
    private String notes;
    private Boolean eventObligatory;

    public Subject(long id, String name, Date date, String summary, String start, String end,
            String roomNumber, String building, String location, String shortDescription, String notes,
            Boolean eventObligatory) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.summary = summary;
        this.start = start;
        this.end = end;
        this.roomNumber = roomNumber;
        this.building = building;
        this.location = location;
        this.shortDescription = shortDescription;
        this.notes = notes;
        this.eventObligatory = eventObligatory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getEventObligatory() {
        return eventObligatory;
    }

    public void setEventObligatory(Boolean eventObligatory) {
        this.eventObligatory = eventObligatory;
    }
}
