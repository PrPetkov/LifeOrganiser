package com.example.lifeorganiser.src.Models.Interfaces;

import com.example.lifeorganiser.src.Models.Exceptions.DBManagerException;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.events.ShoppingList;
import com.example.lifeorganiser.src.Models.events.ShoppingListEntry;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.Models.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.TreeSet;

public interface IDBManager {

    User getUser(String username, String password) throws DBManagerException;

    boolean containsUsername(String username);

    void regUser(String username, String password, String email) throws DBManagerException;

    void addEventsToUsersEvents(TreeSet<DatedEvent> events, int userId);

    void addNotificationEvent(int userId, String eventName, String eventDescription, Calendar dateTime);

    void addPaymentEvent(int userId,
                                int accountID,
                                String eventName,
                                String eventDescription,
                                double amount,
                                boolean isIncome,
                                boolean isPayed,
                                Calendar dateTime);

    void addTODOEvent(int userID, String name, String description, TODOEvent.Type type);

    ArrayList<TODOEvent> getTODOEvents(int userID, TODOEvent.Type type);

    void addDebitAccount(int id, String name, double amount);

    ArrayList<PaymentEvent> getPaymentEvents(int userId, int accountId);

    DebitAccount getDebitAccount(int id, String name, double amount) throws DBManagerException;

    Collection<DebitAccount> getAllDebitAccounts(int id);

    void updateAccount(String name, double amount, int accountId);

    void addShoppingList(String name, int userID, boolean isPayed);

    void addShoppingListEntry(int shoppingListID, String entryName, double price, boolean isTaken);

    ArrayList<ShoppingList> getAllShoppingLists(int userId);

    ShoppingList getShoppingList(int userID, String name) throws DBManagerException;

    void updateEntry(ShoppingListEntry entry, String newName, double newPrice, boolean newIsChecked);

    ShoppingListEntry getLastShoppingListEntry();
}
