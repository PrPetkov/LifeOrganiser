package com.example.lifeorganiser.src.Models.dataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lifeorganiser.src.Models.Exceptions.DBManagerException;
import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;
import com.example.lifeorganiser.src.Models.Interfaces.IDBManager;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.NotificationEvent;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.events.ShoppingList;
import com.example.lifeorganiser.src.Models.events.ShoppingListEntry;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.Models.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.TreeSet;

public class DBAdapter implements IDBManager{

    private DBHelper helper;

    public DBAdapter(Context context) {
        this.helper = new DBHelper(context);
    }

    public void deleteUserFromDB(int userID){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        String[] whereArgs = new String[]{String.valueOf(userID)};

        db.delete(DBHelper.USERS_TABLE_NAME, DBHelper.USERS_UID + "=?", whereArgs);
    }

    public void deleteTODOFromDB(TODOEvent todo, int usersID){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        String[] whereArgs = new String[]{todo.getTitle(), todo.getDescription(), String.valueOf(usersID)};

        db.delete(DBHelper.TABLE_NAME_TODOS, DBHelper.TODO_EVENT_NAME + "=? AND " + DBHelper.TODO_EVENT_DESCRIPTION + "=? AND "
                + DBHelper.USERS_UID + "=?", whereArgs);

    }

    public void deletePaymentEvent(PaymentEvent payment, int userID){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String date = payment.getDateTime().get(Calendar.DAY_OF_MONTH) + "." + payment.getDateTime().get(Calendar.MONTH) + "." +
                payment.getDateTime().get(Calendar.YEAR);
        String time = payment.getDateTime().get(Calendar.HOUR_OF_DAY) + "." + payment.getDateTime().get(Calendar.MINUTE);

        String[] whereArgs = new String[]{String.valueOf(userID), payment.getTitle(), payment.getDescription(),
                String.valueOf(payment.getAmount()), date, time};

        db.delete(DBHelper.PAYMENT_EVENTS_TABLE_NAME, DBHelper.USERS_UID + "=? AND " + DBHelper.PAYMENT_EVENT_NAME + "=? AND " +
                DBHelper.PAYMENT_EVENT_DESCRIPTION + "=? AND " + DBHelper.PAYMENT_EVENT_AMOUNT + "=? AND " + DBHelper.PAYMENT_EVENT_FOR_DATE + "=? AND" +
                DBHelper.PAYMENT_EVENT_FOR_TIME + "=?", whereArgs);
    }

    public void updateUserData(int userID, String username, String password, String email){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(userID)};

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_TABLE_USERNAME_COLUMN, username);
        values.put(DBHelper.USERS_TABLE_PASSWORD_COLUMN, password);
        values.put(DBHelper.USERS_TABLE_EMAIL_COLUMN, email);

        db.update(DBHelper.USERS_TABLE_NAME, values, DBHelper.USERS_UID + "=?", whereArgs);
    }

    @Override
    public void updateEntry(ShoppingListEntry entry, String newName, double newPrice, boolean newIsChecked) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(entry.getDbId())};

        ContentValues values = new ContentValues();

        values.put(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_NAME, newName);
        values.put(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_PRICE, newPrice);
        values.put(DBHelper.SHOPPING_LIST_ENTRIES_IS_TAKEN, newIsChecked);

        db.update(DBHelper.SHOPPING_LIST_ENTRIES_TABLE_NAME,
                values,
                DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_ID + "=?",
                whereArgs);
    }

    public void updateTODOData(int userID, String oldName, String oldDescription, String name, String description) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(userID), oldName, oldDescription};

        ContentValues values = new ContentValues();
        values.put(DBHelper.TODO_EVENT_NAME, name);
        values.put(DBHelper.TODO_EVENT_DESCRIPTION, description);

        db.update(DBHelper.TABLE_NAME_TODOS,
                values,
                DBHelper.USERS_UID + "=? AND " + DBHelper.TODO_EVENT_NAME + "=? AND " + DBHelper.TODO_EVENT_DESCRIPTION + "=?",
                whereArgs);
    }

    @Override
    public void updateAccount(String name, double amount, int accountId) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(accountId)};

        ContentValues values = new ContentValues();
        values.put(DBHelper.ACCOUNTS_NAME, name);
        values.put(DBHelper.ACCOUNTS_AMOUNT, amount);

        db.update(DBHelper.ACCOUNTS_TABLE_NAME,
                values,
                DBHelper.ACCOUNTS_ID + "=?",
                whereArgs);
    }

    @Override
    public boolean containsUsername(String username) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] columns = new String[]{DBHelper.USERS_TABLE_USERNAME_COLUMN};

        Cursor cursor = null;

        try {
            cursor = db.query(DBHelper.USERS_TABLE_NAME, columns,
                    DBHelper.USERS_TABLE_USERNAME_COLUMN + "='" + username + "'",
                    null, null, null, null);
        } catch (Exception e){
            return false;
        }


        if (cursor.moveToNext()){
            return true;
        }

        if (cursor == null || !cursor.isClosed()){
            cursor.close();
        }

        return false;
    }

    @Override
    public User getUser(String username, String password) throws DBManagerException {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        User user = null;

        String[] columns = new String[]{DBHelper.USERS_UID, DBHelper.USERS_TABLE_USERNAME_COLUMN,
                DBHelper.USERS_TABLE_PASSWORD_COLUMN, DBHelper.USERS_TABLE_EMAIL_COLUMN};

        Cursor cursor = db.query(DBHelper.USERS_TABLE_NAME, columns,
                DBHelper.USERS_TABLE_USERNAME_COLUMN + "='" + username + "' AND " + DBHelper.USERS_TABLE_PASSWORD_COLUMN + "='" + password + "'",
                null, null, null, null);

        if (cursor.moveToNext()){
            int uidIndex = cursor.getColumnIndex(DBHelper.USERS_UID);
            int usernameIndex = cursor.getColumnIndex(DBHelper.USERS_TABLE_USERNAME_COLUMN);
            int passwordIndex = cursor.getColumnIndex(DBHelper.USERS_TABLE_PASSWORD_COLUMN);
            int emailIndex = cursor.getColumnIndex(DBHelper.USERS_TABLE_EMAIL_COLUMN);

            user = new User(cursor.getString(usernameIndex), cursor.getString(passwordIndex), cursor.getInt(uidIndex), cursor.getString(emailIndex));
        }

        if (cursor == null || !cursor.isClosed()){
            cursor.close();
        }

        if (user == null){
            throw new DBManagerException("Invalid username or password");
        }

        return user;
    }

    @Override
    public ArrayList<TODOEvent> getTODOEvents(int userID, TODOEvent.Type type){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ArrayList<TODOEvent> todos = new ArrayList<>();

        String[] columns = new String[]{DBHelper.TODO_EVENT_NAME, DBHelper.TODO_EVENT_DESCRIPTION};

        Cursor cursor = db.query(DBHelper.TABLE_NAME_TODOS,
                columns,
                DBHelper.USERS_UID + "=" + userID + " AND " + DBHelper.TODO_EVENT_TYPE + "='" + type.toString() + "'",
                null, null, null, null);

        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(DBHelper.TODO_EVENT_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.TODO_EVENT_DESCRIPTION);

            todos.add(new TODOEvent(cursor.getString(nameIndex), cursor.getString(descriptionIndex)));
        }

        return todos;
    }

    @Override
    public ShoppingList getShoppingList(int userID, String name) throws DBManagerException {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] columns = new String[]{DBHelper.SHOPPING_LIST_LIST_ID, DBHelper.SHOPPING_LIST_LIST_NAME, DBHelper.SHOPPING_LIST_IS_PAID};

        Cursor cursor = db.query(DBHelper.TABLE_NAME_SHOPPING_LISTS,
                columns,
                DBHelper.USERS_UID + "=" + userID + " AND " + DBHelper.SHOPPING_LIST_LIST_NAME + "='" + name + "'",
                null, null, null, null);

        if (cursor.moveToNext()){
            int idIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_LIST_ID);
            int shoppingListNameIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_LIST_NAME);
            int isPayedIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_IS_PAID);

           ShoppingList shoppingList = new ShoppingList(cursor.getString(shoppingListNameIndex),
                    cursor.getInt(idIndex),
                    Boolean.parseBoolean(cursor.getString(isPayedIndex)));

            this.addEntriesToShoppingList(shoppingList);

            return shoppingList;
        }

        throw new DBManagerException("Shopping list not found");
    }

    @Override
    public ArrayList<ShoppingList> getAllShoppingLists(int userId) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ArrayList<ShoppingList> lists = new ArrayList<>();

        String[] columns = new String[]{DBHelper.SHOPPING_LIST_LIST_ID, DBHelper.SHOPPING_LIST_LIST_NAME, DBHelper.SHOPPING_LIST_IS_PAID};

        Cursor cursor = db.query(DBHelper.TABLE_NAME_SHOPPING_LISTS,
                columns,
                DBHelper.USERS_UID + "=" + userId,
                null, null, null, null);

        while (cursor.moveToNext()){
            int idIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_LIST_ID);
            int shoppingListNameIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_LIST_NAME);
            int isPayedIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_IS_PAID);

            ShoppingList shoppingList = new ShoppingList(cursor.getString(shoppingListNameIndex),
                    cursor.getInt(idIndex),
                    Boolean.parseBoolean(cursor.getString(isPayedIndex)));

            this.addEntriesToShoppingList(shoppingList);

            lists.add(shoppingList);
        }

        return lists;
    }

    private void addEntriesToShoppingList(ShoppingList shoppingList){
        int shoppingListID = shoppingList.getDbID();

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = new String[]{DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_NAME,
                DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_PRICE,
                DBHelper.SHOPPING_LIST_ENTRIES_IS_TAKEN,
                DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_ID};

        Cursor cursor = db.query(DBHelper.SHOPPING_LIST_ENTRIES_TABLE_NAME,
                columns,
                DBHelper.SHOPPING_LIST_LIST_ID  + "=" + shoppingListID,
                null, null, null, null);

        while (cursor.moveToNext()){
            int entryNameIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_NAME);
            int price = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_PRICE);
            int isTakenIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_IS_TAKEN);
            int entryId = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_ID);

            boolean isTaken = (cursor.getInt(isTakenIndex) > 0);

            shoppingList.addEntry(new ShoppingListEntry(cursor.getString(entryNameIndex),
                    cursor.getDouble(price),
                    isTaken,
                    cursor.getInt(entryId)));
        }
    }

    @Override
    public ShoppingListEntry getLastShoppingListEntry() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = new String[]{DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_NAME,
                DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_PRICE,
                DBHelper.SHOPPING_LIST_ENTRIES_IS_TAKEN,
                DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_ID};

        Cursor cursor = db.query(DBHelper.SHOPPING_LIST_ENTRIES_TABLE_NAME,
                columns,
                DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_ID  + "=(SELECT max(" + DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_ID + ") FROM "+ DBHelper.SHOPPING_LIST_ENTRIES_TABLE_NAME + ")",
                null, null, null, null);

        if (cursor.moveToNext()){
            int entryNameIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_NAME);
            int price = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_PRICE);
            int isTakenIndex = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_IS_TAKEN);
            int entryId = cursor.getColumnIndex(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_ID);

            return new ShoppingListEntry(cursor.getString(entryNameIndex),
                    cursor.getDouble(price),
                    Boolean.parseBoolean(cursor.getString(isTakenIndex)),
                    cursor.getInt(entryId));
        }

        return null;
    }

    @Override
    public DebitAccount getDebitAccount(int id, String name, double amount) throws DBManagerException {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] columns = new String[]{DBHelper.ACCOUNTS_ID, DBHelper.ACCOUNTS_NAME, DBHelper.ACCOUNTS_AMOUNT};

        Cursor cursor = db.query(DBHelper.ACCOUNTS_TABLE_NAME,
                columns,
                DBHelper.USERS_UID + "=" + id + " AND " + DBHelper.ACCOUNTS_NAME + "='" + name + "'",
                null, null, null, null);

        if (cursor.moveToNext()){
            int idIndex = cursor.getColumnIndex(DBHelper.ACCOUNTS_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.ACCOUNTS_NAME);
            int amountIndex = cursor.getColumnIndex(DBHelper.ACCOUNTS_AMOUNT);

            return new DebitAccount(cursor.getString(nameIndex), cursor.getDouble(amountIndex), cursor.getInt(idIndex));
        }

        throw new DBManagerException("Account not found");
    }

    @Override
    public Collection<DebitAccount> getAllDebitAccounts(int id) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] columns = new String[]{DBHelper.ACCOUNTS_ID, DBHelper.ACCOUNTS_NAME, DBHelper.ACCOUNTS_AMOUNT};

        Cursor cursor = db.query(DBHelper.ACCOUNTS_TABLE_NAME,
                columns,
                DBHelper.USERS_UID + "=" + id,
                null, null, null, null);

        ArrayList<DebitAccount> accounts = new ArrayList<DebitAccount>();

        while (cursor.moveToNext()){
            int idIndex = cursor.getColumnIndex(DBHelper.ACCOUNTS_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.ACCOUNTS_NAME);
            int amountIndex = cursor.getColumnIndex(DBHelper.ACCOUNTS_AMOUNT);

            accounts.add(new DebitAccount(cursor.getString(nameIndex), cursor.getDouble(amountIndex), cursor.getInt(idIndex)));
        }

        return accounts;
    }

    @Override
    public void addEventsToUsersEvents(TreeSet<DatedEvent> events, int userId) {
        ArrayList<DatedEvent> eventsFromdb = new ArrayList<>();
        events.addAll(this.getPaymentEventsFromDB(userId));
        events.addAll(this.getNotificationEventsFromDB(userId));
    }

    private ArrayList<NotificationEvent> getNotificationEventsFromDB(int userId){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ArrayList<NotificationEvent> events = new ArrayList<>();

        String[] columns = new String[]{DBHelper.NOTIFICATION_EVENTS_NAME,
                DBHelper.NOTIFICATION_EVENTS_DESCRIPTION,
                DBHelper.NOTIFICATION_EVENTS_DATE,
                DBHelper.NOTIFICATION_EVENTS_TIME};

        Cursor cursor = db.query(DBHelper.NOTIFICATION_EVENTS_TABLE_NAME, columns,
                DBHelper.USERS_UID + "=" + userId, null, null, null, null);

        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(DBHelper.NOTIFICATION_EVENTS_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.NOTIFICATION_EVENTS_DESCRIPTION);
            int dateIndex = cursor.getColumnIndex(DBHelper.NOTIFICATION_EVENTS_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.NOTIFICATION_EVENTS_TIME);

            String[] date = cursor.getString(dateIndex).split("-");
            String[] time = cursor.getString(timeIndex).split("\\.");


//            if (time.length < 2){
//                time = new String[]{"0", "0"};
//            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
                    Integer.parseInt(time[0]), Integer.parseInt(time[1]));

                events.add(new NotificationEvent(cursor.getString(nameIndex), cursor.getString(descriptionIndex), calendar));

        }

        return events;
    }

    private ArrayList<PaymentEvent> getPaymentEventsFromDB(int userID){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ArrayList<PaymentEvent> paymentEvents = new ArrayList<>();

        String[] columns = new String[]{DBHelper.PAYMENT_EVENT_NAME, DBHelper.PAYMENT_EVENT_DESCRIPTION, DBHelper.PAYMENT_EVENT_AMOUNT,
            DBHelper.PAYMENT_EVENT_IS_PAYED, DBHelper.PAYMENT_EVENT_FOR_DATE, DBHelper.PAYMENT_EVENT_FOR_TIME};

        Cursor cursor = db.query(DBHelper.PAYMENT_EVENTS_TABLE_NAME, columns,
                DBHelper.USERS_UID + "=" + userID, null, null, null, null);

        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_DESCRIPTION);
            int amountIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_AMOUNT);
            int isPayedIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_IS_PAYED);
            int dateIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_FOR_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_FOR_TIME);

            String[] date = cursor.getString(dateIndex).split("-");
            String[] time = cursor.getString(timeIndex).split("\\.");
            //TODO bug
            if (time.length < 2){
                time = new String[]{"0", "0"};
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
                    Integer.parseInt(time[0]), Integer.parseInt(time[1]));

            try {
                paymentEvents.add(new PaymentEvent(cursor.getString(nameIndex),cursor.getString(descriptionIndex), cursor.getDouble(amountIndex),
                        true, Boolean.parseBoolean(cursor.getString(isPayedIndex)), calendar));
            } catch (IllegalAmountException e) {
                e.printStackTrace();
            }
        }

        cursor.close();

        return paymentEvents;
    }

    @Override
    public ArrayList<PaymentEvent> getPaymentEvents(int userId, int accountId) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ArrayList<PaymentEvent> paymentEvents = new ArrayList<>();

        String[] columns = new String[]{DBHelper.PAYMENT_EVENT_NAME, DBHelper.PAYMENT_EVENT_DESCRIPTION, DBHelper.PAYMENT_EVENT_AMOUNT,
                DBHelper.PAYMENT_EVENT_IS_PAYED, DBHelper.PAYMENT_EVENT_FOR_DATE, DBHelper.PAYMENT_EVENT_FOR_TIME, DBHelper.PAYMENT_EVENT_IS_INCOME};

        Cursor cursor = db.query(DBHelper.PAYMENT_EVENTS_TABLE_NAME,
                columns,
                DBHelper.USERS_UID + "=" + userId + " AND " + DBHelper.ACCOUNTS_ID + "=" + accountId,
                null, null, null, null);

        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_DESCRIPTION);
            int amountIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_AMOUNT);
            int isPayedIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_IS_PAYED);
            int dateIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_FOR_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_FOR_TIME);
            int isIncomeIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_IS_INCOME);

            String[] date = cursor.getString(dateIndex).split("-");
            String[] time = cursor.getString(timeIndex).split("\\.");

            if (time.length < 2){
                time = new String[]{"0", "0"};
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
                    Integer.parseInt(time[0]), Integer.parseInt(time[1]));

            boolean isIncome = cursor.getInt(isIncomeIndex) > 0;
            boolean isPaid = cursor.getInt(isPayedIndex) > 0;

            try {
                paymentEvents.add(new PaymentEvent(cursor.getString(nameIndex),cursor.getString(descriptionIndex), cursor.getDouble(amountIndex),
                        isIncome, isPaid, calendar));
            } catch (IllegalAmountException e) {
                e.printStackTrace();
            }
        }

        cursor.close();

        return paymentEvents;
    }

    @Override
    public void regUser(String username, String password, String email) throws DBManagerException {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_TABLE_USERNAME_COLUMN, username);
        values.put(DBHelper.USERS_TABLE_PASSWORD_COLUMN, password);
        values.put(DBHelper.USERS_TABLE_EMAIL_COLUMN, email);

        try {
            db.insert(DBHelper.USERS_TABLE_NAME, null, values);
        }catch (Exception e){
            throw new DBManagerException(e.getMessage(), e);
        }
    }

    @Override
    public void addPaymentEvent(int userId, int accountID, String eventName, String eventDescription,
                                    double amount, boolean isIncome, boolean isPayed, Calendar dateTime){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String date = dateTime.get(Calendar.YEAR) + "-" + dateTime.get(Calendar.MONTH) + "-" + dateTime.get(Calendar.DAY_OF_MONTH);
        String time = dateTime.get(Calendar.HOUR_OF_DAY) + "." + dateTime.get(Calendar.MINUTE);

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_UID, userId);
        values.put(DBHelper.PAYMENT_EVENT_NAME, eventName);
        values.put(DBHelper.PAYMENT_EVENT_DESCRIPTION, eventDescription);
        values.put(DBHelper.PAYMENT_EVENT_AMOUNT, amount);
        values.put(DBHelper.PAYMENT_EVENT_IS_PAYED, isPayed);
        values.put(DBHelper.PAYMENT_EVENT_FOR_DATE, date);
        values.put(DBHelper.PAYMENT_EVENT_IS_INCOME, isIncome);
        values.put(DBHelper.PAYMENT_EVENT_FOR_TIME, time);
        values.put(DBHelper.ACCOUNTS_ID, accountID);

        db.insert(DBHelper.PAYMENT_EVENTS_TABLE_NAME, null, values);
    }

    @Override
    public void addNotificationEvent(int userId, String eventName, String eventDescription, Calendar dateTime){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String date = dateTime.get(Calendar.YEAR) + "-" + dateTime.get(Calendar.MONTH) + "-" + dateTime.get(Calendar.DAY_OF_MONTH);
        String time = dateTime.get(Calendar.HOUR_OF_DAY) + "." + dateTime.get(Calendar.MINUTE);

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_UID, userId);
        values.put(DBHelper.NOTIFICATION_EVENTS_NAME, eventName);
        values.put(DBHelper.NOTIFICATION_EVENTS_DESCRIPTION, eventDescription);
        values.put(DBHelper.NOTIFICATION_EVENTS_DATE, date);
        values.put(DBHelper.NOTIFICATION_EVENTS_TIME, time);

        db.insert(DBHelper.NOTIFICATION_EVENTS_TABLE_NAME, null, values);
    }


    @Override
    public void addTODOEvent(int userID, String name, String description, TODOEvent.Type type){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_UID, userID);
        values.put(DBHelper.TODO_EVENT_NAME, name);
        values.put(DBHelper.TODO_EVENT_DESCRIPTION, description);
        values.put(DBHelper.TODO_EVENT_TYPE, type.toString());

        db.insert(DBHelper.TABLE_NAME_TODOS, null, values);
    }

    @Override
    public void addDebitAccount(int id, String name, double amount) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_UID, id);
        values.put(DBHelper.ACCOUNTS_NAME, name);
        values.put(DBHelper.ACCOUNTS_AMOUNT, amount);

        db.insert(DBHelper.ACCOUNTS_TABLE_NAME, null, values);
    }

    @Override
    public void addShoppingList(String name, int userID, boolean isPayed){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBHelper.SHOPPING_LIST_LIST_NAME, name);
        values.put(DBHelper.USERS_UID, userID);
        values.put(DBHelper.SHOPPING_LIST_IS_PAID, isPayed);

        db.insert(DBHelper.TABLE_NAME_SHOPPING_LISTS, null, values);
    }

    @Override
    public void addShoppingListEntry(int shoppingListID, String entryName, double price, boolean isTaken) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_NAME, entryName);
        values.put(DBHelper.SHOPPING_LIST_ENTRIES_ENTRY_PRICE, price);
        values.put(DBHelper.SHOPPING_LIST_ENTRIES_IS_TAKEN, isTaken);
        values.put(DBHelper.SHOPPING_LIST_LIST_ID, shoppingListID);

        db.insert(DBHelper.SHOPPING_LIST_ENTRIES_TABLE_NAME, null, values);
    }

    class DBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "lo";
        private static final int DATABASE_VERSION = 1;

        private static final String USERS_TABLE_NAME = "users";
        private static final String USERS_UID = "user_id";
        private static final String USERS_TABLE_USERNAME_COLUMN = "username";
        private static final String USERS_TABLE_PASSWORD_COLUMN = "password";
        private static final String USERS_TABLE_EMAIL_COLUMN = "email";

        private static final String PAYMENT_EVENTS_TABLE_NAME = "payment_events";
        private static final String PAYMENT_EVENTS_ID = "pe_id";
        private static final String PAYMENT_EVENT_NAME = "pe_name";
        private static final String PAYMENT_EVENT_DESCRIPTION = "description";
        private static final String PAYMENT_EVENT_AMOUNT = "ammount";
        private static final String PAYMENT_EVENT_IS_PAYED = "is_payed";
        private static final String PAYMENT_EVENT_FOR_DATE = "for_date";
        private static final String PAYMENT_EVENT_FOR_TIME = "for_time";
        private static final String PAYMENT_EVENT_IS_INCOME = "is_income";

        private static final String TABLE_NAME_TODOS = "todos";
        private static final String TODO_EVENTS_ID = "pe_id";
        private static final String TODO_EVENT_NAME = "pe_name";
        private static final String TODO_EVENT_DESCRIPTION = "description";
        private static final String TODO_EVENT_TYPE = "type";

        private static final String TABLE_NAME_SHOPPING_LISTS = "shopping_lists";
        private static final String SHOPPING_LIST_LIST_NAME = "list_name";
        private static final String SHOPPING_LIST_LIST_ID = "list_id";

        private static final String NOTIFICATION_EVENTS_TABLE_NAME = "notification_events";
        private static final String NOTIFICATION_EVENTS_ID = "ne_id";
        private static final String NOTIFICATION_EVENTS_NAME = "ne_name";
        private static final String NOTIFICATION_EVENTS_DESCRIPTION = "description";
        private static final String NOTIFICATION_EVENTS_DATE = "for_date";
        private static final String NOTIFICATION_EVENTS_TIME = "for_time";

        private static final String ACCOUNTS_TABLE_NAME = "accounts";
        private static final String ACCOUNTS_ID = "accounts_id";
        private static final String ACCOUNTS_NAME = "accounts_name";
        private static final String ACCOUNTS_AMOUNT = "accounts_amount";

        private static final String SHOPPING_LIST_ENTRIES_TABLE_NAME = "shopping_list_entries";
        private static final String SHOPPING_LIST_ENTRIES_ENTRY_ID = "entries_id";
        private static final String SHOPPING_LIST_ENTRIES_ENTRY_NAME = "entry_name";
        private static final String SHOPPING_LIST_ENTRIES_ENTRY_PRICE = "entry_price";
        private static final String SHOPPING_LIST_IS_PAID = "list_is_paid";
        private static final String SHOPPING_LIST_ENTRIES_IS_TAKEN = "isTaken";


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " ("
                    + USERS_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERS_TABLE_USERNAME_COLUMN + " VARCHAR(25) UNIQUE NOT NULL, " +
                    USERS_TABLE_PASSWORD_COLUMN + " VARCHAR(255) NOT NULL, " +
                    USERS_TABLE_EMAIL_COLUMN + " VARCHAR(25) UNIQUE NOT NULL" +
                    ");");

            db.execSQL("CREATE TABLE " + PAYMENT_EVENTS_TABLE_NAME + " (" +
                    PAYMENT_EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBHelper.USERS_UID + " INTEGER NOT NULL," +
                    PAYMENT_EVENT_NAME + " VARCHAR(25) NOT NULL," +
                    PAYMENT_EVENT_DESCRIPTION + " VARCHAR(255), " +
                    PAYMENT_EVENT_AMOUNT + " DOUBLE PRECISION UNSIGNED," +
                    PAYMENT_EVENT_IS_INCOME + " BOOLEAN NOT NULL," +
                    PAYMENT_EVENT_IS_PAYED + " BOOLEAN NOT NULL," +
                    PAYMENT_EVENT_FOR_DATE + " DATE NOT NULL," +
                    PAYMENT_EVENT_FOR_TIME + " TIME NOT NULL," +
                    ACCOUNTS_ID + " INTEGER," +
                    "FOREIGN KEY (" + DBHelper.ACCOUNTS_ID + ") REFERENCES " + DBHelper.ACCOUNTS_TABLE_NAME + "(" + DBHelper.ACCOUNTS_ID + ")" +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + TABLE_NAME_TODOS + " (" +
                    TODO_EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBHelper.USERS_UID + " INTEGER NOT NULL," +
                    TODO_EVENT_NAME + " VARCHAR(25) NOT NULL," +
                    TODO_EVENT_DESCRIPTION + " VARCHAR(255)," +
                    TODO_EVENT_TYPE + " VARCHAR(25) NOT NULL," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + TABLE_NAME_SHOPPING_LISTS + " (" +
                    SHOPPING_LIST_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBHelper.USERS_UID + " INTEGER NOT NULL," +
                    SHOPPING_LIST_LIST_NAME + " VARCHAR(100)," +
                    SHOPPING_LIST_IS_PAID + " BOOLEAN NOT NULL," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + NOTIFICATION_EVENTS_TABLE_NAME + " (" +
                    NOTIFICATION_EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBHelper.USERS_UID + " INTEGER NOT NULL," +
                    NOTIFICATION_EVENTS_NAME + " VARCHAR(25) NOT NULL," +
                    NOTIFICATION_EVENTS_DESCRIPTION + " VARCHAR(255), " +
                    NOTIFICATION_EVENTS_DATE + " DATE NOT NULL," +
                    NOTIFICATION_EVENTS_TIME + " TIME NOT NULL," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + SHOPPING_LIST_ENTRIES_TABLE_NAME + " ("
                    + SHOPPING_LIST_ENTRIES_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SHOPPING_LIST_LIST_ID + " INTEGER NOT NULL, " +
                    SHOPPING_LIST_ENTRIES_ENTRY_NAME + " INTEGER NOT NULL, " +
                    SHOPPING_LIST_ENTRIES_ENTRY_PRICE + " DOUBLE PRECISION," +
                    SHOPPING_LIST_ENTRIES_IS_TAKEN + " BOOLEAN NOT NULL, " +
                    "FOREIGN KEY (" + SHOPPING_LIST_LIST_ID + ") REFERENCES " + TABLE_NAME_SHOPPING_LISTS + "(" + SHOPPING_LIST_LIST_ID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + ACCOUNTS_TABLE_NAME + " (" +
                    ACCOUNTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBHelper.USERS_UID + " INTEGER NOT NULL, " +
                    ACCOUNTS_NAME + " VARCHAR(255) NOT NULL, " +
                    ACCOUNTS_AMOUNT + " DOUBLE PRECISION," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST " + USERS_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXIST " + PAYMENT_EVENTS_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME_TODOS);
            db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME_SHOPPING_LISTS);
            db.execSQL("DROP TABLE IF EXIST " + NOTIFICATION_EVENTS_TABLE_NAME);

            this.onCreate(db);
        }
    }
}
