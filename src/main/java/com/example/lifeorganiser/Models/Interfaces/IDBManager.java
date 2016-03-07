package com.example.lifeorganiser.src.Models.Interfaces;

import com.example.lifeorganiser.src.Models.Exceptions.DBManagerException;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.Models.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeSet;

public interface IDBManager {

    User getUser(String username, String password) throws DBManagerException;

    boolean containsUsername(String username);

    void regUser(String username, String password, String email) throws DBManagerException;

    void addEventsToUsersEvents(TreeSet<DatedEvent> events, int userId);

    void addNotificationEvent(int userId, String eventName, String eventDescription, Calendar dateTime);

    void addPaymentEvent(int userId,
                                String eventName,
                                String eventDescription,
                                double amount,
                                boolean isPayed,
                                Calendar dateTime);

    void addTODOEvent(int userID, String name, String description, TODOEvent.Type type);

    ArrayList<TODOEvent> getTODOEvents(int userID, TODOEvent.Type type);
}
