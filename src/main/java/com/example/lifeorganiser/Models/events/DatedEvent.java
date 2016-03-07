package com.example.lifeorganiser.src.Models.events;


import java.util.Calendar;

public abstract class DatedEvent extends Event {

    private Calendar dateTime;

    DatedEvent(String title, String description, Calendar dateTime) {
        super(title, description);

        this.dateTime = dateTime;
    }

    public Calendar getDateTime() {
        return dateTime;
    }
}
