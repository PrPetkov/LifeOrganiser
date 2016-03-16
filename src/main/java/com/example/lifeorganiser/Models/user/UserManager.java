package com.example.lifeorganiser.src.Models.user;

import com.example.lifeorganiser.src.Models.Exceptions.DBManagerException;
import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;
import com.example.lifeorganiser.src.Models.Exceptions.UserManagerException;
import com.example.lifeorganiser.src.Models.Interfaces.IDBManager;
import com.example.lifeorganiser.src.Models.accounts.Account;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.dataBase.DBAdapter;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.NotificationEvent;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.events.ShoppingList;
import com.example.lifeorganiser.src.Models.events.ShoppingListEntry;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.controllers.activities.LogInActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    public static final int MIN_USERNAME_LENGTH = 4;
    public static final int MIN_PASSWORD_LENGTH = 5;

    private Crypter crypter;
    private User user;
    private static UserManager manager;
    private IDBManager dbManager;

    private UserManager() {
        this.dbManager = new DBAdapter(LogInActivity.getContext());
       this.crypter = Crypter.getInstance();
    }

    public static UserManager getInstance(){
        if (UserManager.manager == null){
            UserManager.manager = new UserManager();
        }

        return UserManager.manager;
    }

    public TreeSet<DatedEvent> getTasks(){
        TreeSet<DatedEvent> tasks = this.user.getEvents();

        if (tasks.size() == 0){
            this.dbManager.addEventsToUsersEvents(tasks, this.user.getId());
        }

        return tasks;
    }

    public ArrayList<PaymentEvent> getAccountHistory(int accountId){
        return this.dbManager.getPaymentEvents(this.user.getId(), accountId);
    }

    public void addEvent(PaymentEvent event, int accountId){
        this.user.addEvent(event);


        this.dbManager.addPaymentEvent(this.user.getId(),
                accountId,
                event.getTitle(),
                event.getDescription(),
                event.getAmount(),
                event.getIsIncome(),
                event.getIsPaid(),
                event.getDateTime());

    }

    public void addEvent(NotificationEvent event){
        this.user.addEvent(event);

        this.dbManager.addNotificationEvent(this.user.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDateTime());

    }

    public String getUsername(){
        return this.user.getUserName();
    }

    public void logUser(String username, String password) throws UserManagerException {
        String cryptedPassword = this.crypter.encrypt(password);

        try {
            this.user = this.dbManager.getUser(username, cryptedPassword);
        } catch (DBManagerException e) {
            throw new UserManagerException(e.getMessage(), e);
        }

        if (this.getDebitAccounts().size() == 0){
            this.addDebitAccounts("Default account", 0);
        }
    }

    public void regUser(String username, String password, String email) throws UserManagerException {
        if (username == null || password == null || email == null){
            throw new UserManagerException("values can not be null");
        }

        if (!this.validateEmail(email)){
            throw new UserManagerException("Invalid email");
        }

        if (!this.validateUsername(username)){
            throw new UserManagerException("username must be at least " + UserManager.MIN_USERNAME_LENGTH + " chars long");
        }

        if (password.length() < UserManager.MIN_PASSWORD_LENGTH){
            throw new UserManagerException("password must be at least " + UserManager.MIN_PASSWORD_LENGTH + " chars long");
        }

        String cryptedPassword = this.crypter.encrypt(password);

        try {
            this.dbManager.regUser(username, cryptedPassword, email);
        } catch (DBManagerException e) {
            throw new UserManagerException(e.getMessage(), e);
        }
    }

    public void addTodo(TODOEvent.Type type, TODOEvent event){
        this.user.addTODO(type, event);
        this.dbManager.addTODOEvent(this.user.getId(), event.getTitle(), event.getDescription(), type);
    }

    public ArrayList<TODOEvent> getTodos(TODOEvent.Type type){
        ArrayList<TODOEvent> events = this.user.getTodos(type);

        if (events.size() == 0){
            events.addAll(this.dbManager.getTODOEvents(this.user.getId(), type));
        }

        return events;
    }

    public ArrayList<DebitAccount> getDebitAccounts(){
        if (this.user.getDebitAccounts().size() == 0){
            this.user.getDebitAccounts().addAll(this.dbManager.getAllDebitAccounts(this.user.getId()));
        }
        return this.user.getDebitAccounts();
    }

    public void addDebitAccounts(String name, double amount) throws UserManagerException {

        this.dbManager.addDebitAccount(this.user.getId(), name, amount);

        DebitAccount account = null;
        try {
            account = this.dbManager.getDebitAccount(this.user.getId(), name, amount);
        } catch (DBManagerException e) {
            throw new UserManagerException("Account not saved", e);
        }

        this.user.addDebitAccount(account);
    }

    public void withdrawMoney(Account account, double amount) throws UserManagerException {
        try {
            this.addEvent(new PaymentEvent("Withdraw money",
                    "Auto generated event",
                    amount,
                    false, true,
                    Calendar.getInstance()), account.getDbUid());

            account.withdrawMoney(amount);
        } catch (IllegalAmountException e) {
            throw new UserManagerException("Account not saved", e);
        }

        this.dbManager.updateAccount(account.getAccountName(), account.getAmount(), account.getDbUid());
    }

    public void pay(Account account, PaymentEvent paymentEvent){
        this.addEvent(paymentEvent, account.getDbUid());
        account.withdrawMoney(paymentEvent.getAmount());
        this.dbManager.updateAccount(account.getAccountName(), account.getAmount(), account.getDbUid());
    }

    public void addMoney(Account account, double amount) throws UserManagerException {
        try {
           this.addEvent(new PaymentEvent("Add money",
                    "Auto generated event",
                    amount,
                    true, true,
                    Calendar.getInstance()), account.getDbUid());

            account.insertMoney(amount);
        } catch (IllegalAmountException e) {
            throw new  UserManagerException("The amount must be positive", e);
        }

        this.dbManager.updateAccount(account.getAccountName(), account.getAmount(), account.getDbUid());
    }

    public ArrayList<ShoppingList> getAllShoppingLists(){
        ArrayList<ShoppingList> lists = this.user.getAllShoppingLists();

        if (lists.size() == 0){
            lists.addAll(this.dbManager.getAllShoppingLists(this.user.getId()));
        }

        return lists;
    }

    public void addShoppingList(String name, boolean isPayed) throws UserManagerException {
        this.dbManager.addShoppingList(name, this.user.getId(), isPayed);

        try {
            this.user.addShoppingList(this.dbManager.getShoppingList(this.user.getId(), name));
        } catch (DBManagerException e) {
            throw new UserManagerException("ShoppingList not added", e);
        }
    }

    public void addShoppingListEntry(ShoppingList shoppingList, String entryName, double price, boolean isTaken){
        this.dbManager.addShoppingListEntry(shoppingList.getDbID(), entryName, price, isTaken);

        ShoppingListEntry entry = this.dbManager.getLastShoppingListEntry();

        shoppingList.addEntry(entry);
    }

    private boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z][\\w\\.]+@[a-z]+\\.[a-z]+\\.*[a-z]*$");
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    private boolean validateUsername(String username) throws UserManagerException {
        if (username.length() < UserManager.MIN_USERNAME_LENGTH){
            throw new UserManagerException("username must be at least " + UserManager.MIN_USERNAME_LENGTH + " chars long");
        }

        if (!Character.isLetter(username.charAt(0))){
            throw new UserManagerException("username must start with letter");
        }

        if (this.dbManager.containsUsername(username)){
            throw new UserManagerException("username already taken");
        }

        return true;
    }

    public void updateShoppingListEntry(ShoppingListEntry entry, String name, double newPrice, boolean isTaken) {
        this.dbManager.updateEntry(entry, name, newPrice, isTaken);

        entry.update(name, newPrice, isTaken);
    }

    public boolean hasLoggedUser() {
        return this.user != null;
    }
}
