package com.example.calendarapptrial;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    public static ArrayList<Event> eventsList = new ArrayList<>();
    public static ArrayList<Event> deletedList = new ArrayList<>();
    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : eventsList) {
            if (event.getDate().equals(date) && !events.contains(event) && !deletedList.contains(event)) {
                events.add(event);
            }
        }
        return events;
    }
    private String name;
    private LocalDate date;
    private LocalTime time;
    private String eventLocation;

    public Event(String name, LocalDate date, LocalTime time, String eventLocation) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.eventLocation = eventLocation;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getEventLocation() { return eventLocation; }
    public void setEventLocation() { this.eventLocation = eventLocation; }
}
