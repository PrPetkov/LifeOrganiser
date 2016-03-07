package com.example.lifeorganiser.src.Models.events;


import java.util.Calendar;

public class NotificationEvent extends DatedEvent {

    public NotificationEvent(String title, String description, Calendar dateTime) {
        super(title, description, dateTime);
    }
}
