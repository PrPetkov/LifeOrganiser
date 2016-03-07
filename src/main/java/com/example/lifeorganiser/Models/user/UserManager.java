package com.example.lifeorganiser.src.Models.user;

import com.example.lifeorganiser.src.Models.Exceptions.DBManagerException;
import com.example.lifeorganiser.src.Models.Exceptions.UserManagerException;
import com.example.lifeorganiser.src.Models.Interfaces.IDBManager;
import com.example.lifeorganiser.src.Models.accounts.Account;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.dataBase.DBAdapter;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.Event;
import com.example.lifeorganiser.src.Models.events.NotificationEvent;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.controllers.LogInActivity;

import java.util.ArrayList;
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

    public void addEvent(DatedEvent event){
        this.user.addEvent(event);

        if (event instanceof PaymentEvent){
            this.dbManager.addPaymentEvent(this.user.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    ((PaymentEvent)event).getAmount(),
                    ((PaymentEvent)event).getIsPaid(),
                    event.getDateTime());
        }else if (event instanceof NotificationEvent){
            this.dbManager.addNotificationEvent(this.user.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getDateTime());
        }
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
        //TODO DB
        return this.user.getDebitAccounts();
    }

    public void addDebitAccounts(DebitAccount account){
        //TODO DB
        this.user.addDebitAccount(account);
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
}
