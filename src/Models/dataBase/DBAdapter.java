package com.example.lifeorganiser.src.Models.dataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;
import com.example.lifeorganiser.src.Models.Interfaces.IDBManager;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.Models.user.User;

import java.util.ArrayList;
import java.util.Calendar;

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
                DBHelper.PAYMENT_EVENT_DESCRIPTION + "=? AND " +DBHelper.PAYMENT_EVENT_AMOUNT + "=? AND " + DBHelper.PAYMENT_EVENT_FOR_DATE + "=? AND" +
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

    public void updateTODOData(int userID, String oldName, String oldDescription, String name, String description) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(userID), oldName, oldDescription};

        ContentValues values = new ContentValues();
        values.put(DBHelper.TODO_EVENT_NAME, name);
        values.put(DBHelper.TODO_EVENT_DESCRIPTION, description);

        db.update(DBHelper.TABLE_NAME_TODOS, values, DBHelper.USERS_UID + "=? AND " + DBHelper.TODO_EVENT_NAME + "=? AND " +
            DBHelper.TODO_EVENT_DESCRIPTION + "=?", whereArgs);
    }

    public User getUserFromDB(String username, String password){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        User user = null;

        String[] columns = new String[]{DBHelper.USERS_UID, DBHelper.USERS_TABLE_USERNAME_COLUMN,
                DBHelper.USERS_TABLE_PASSWORD_COLUMN, DBHelper.USERS_TABLE_EMAIL_COLUMN};

        Cursor cursor = db.query(DBHelper.USERS_TABLE_NAME, columns,
                "WHERE " + DBHelper.USERS_TABLE_USERNAME_COLUMN + "=" + username + " AND " + DBHelper.USERS_TABLE_PASSWORD_COLUMN + "=" + password,
                null, null, null, null);

        if (cursor.moveToNext()){
            int uidIndex = cursor.getColumnIndex(DBHelper.USERS_UID);
            int usernameIndex = cursor.getColumnIndex(DBHelper.USERS_TABLE_USERNAME_COLUMN);
            int passwordIndex = cursor.getColumnIndex(DBHelper.USERS_TABLE_PASSWORD_COLUMN);
            int emailIndex = cursor.getColumnIndex(DBHelper.USERS_TABLE_EMAIL_COLUMN);

            user = new User(cursor.getString(usernameIndex), cursor.getString(passwordIndex), cursor.getInt(uidIndex), cursor.getString(emailIndex));
        }

        return user;
    }

    public ArrayList<TODOEvent> getTODOSFromDB(int userID){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ArrayList<TODOEvent> todos = new ArrayList<>();

        String[] columns = new String[]{DBHelper.TODO_EVENT_NAME, DBHelper.TODO_EVENT_DESCRIPTION};

        Cursor cursor = db.query(DBHelper.TABLE_NAME_TODOS, columns,
                "WHERE " + DBHelper.USERS_UID + "=" + userID, null, null, null, null);

        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(DBHelper.TODO_EVENT_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.TODO_EVENT_DESCRIPTION);

            todos.add(new TODOEvent(cursor.getString(nameIndex), cursor.getString(descriptionIndex)));
        }

        return todos;
    }

    public ArrayList<PaymentEvent> getPaymentEventsFromDB(int userID){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ArrayList<PaymentEvent> paymentEvents = new ArrayList<>();

        String[] columns = new String[]{DBHelper.PAYMENT_EVENT_NAME, DBHelper.PAYMENT_EVENT_DESCRIPTION, DBHelper.PAYMENT_EVENT_AMOUNT,
            DBHelper.PAYMENT_EVENT_IS_PAYED, DBHelper.PAYMENT_EVENT_FOR_DATE, DBHelper.PAYMENT_EVENT_FOR_TIME};

        Cursor cursor = db.query(DBHelper.PAYMENT_EVENTS_TABLE_NAME, columns,
                "WHERE " + DBHelper.USERS_UID + "=" + userID, null, null, null, null);

        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_DESCRIPTION);
            int amountIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_AMOUNT);
            int isPayedIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_IS_PAYED);
            int dateIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_FOR_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.PAYMENT_EVENT_FOR_TIME);

            String[] date = cursor.getString(dateIndex).split("\\.");
            String[] time = cursor.getString(timeIndex).split("\\.");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
                    Integer.parseInt(time[0]), Integer.parseInt(time[1]));

            try {
                paymentEvents.add(new PaymentEvent(cursor.getString(nameIndex),cursor.getString(descriptionIndex), cursor.getDouble(amountIndex),
                        true, Boolean.parseBoolean(cursor.getString(isPayedIndex))));
            } catch (IllegalAmountException e) {
                e.printStackTrace();
            }
        }

        return paymentEvents;
    }

    public void addUserData(String username, String password, String email){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_TABLE_USERNAME_COLUMN, username);
        values.put(DBHelper.USERS_TABLE_PASSWORD_COLUMN, password);
        values.put(DBHelper.USERS_TABLE_EMAIL_COLUMN, email);

        db.insert(DBHelper.USERS_TABLE_NAME, null, values);
    }

    public void addPaymentEventData(int usserId, String eventName, String eventDescription,
                                    double amount, boolean isPayed, boolean isOverDue, Calendar dateTime){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        String date = dateTime.get(Calendar.DAY_OF_MONTH) + "." + dateTime.get(Calendar.MONTH) + "." + dateTime.get(Calendar.YEAR);
        String time = dateTime.get(Calendar.HOUR_OF_DAY) + "." + dateTime.get(Calendar.MINUTE);

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_UID, usserId);
        values.put(DBHelper.PAYMENT_EVENT_NAME, eventName);
        values.put(DBHelper.PAYMENT_EVENT_DESCRIPTION, eventDescription);
        values.put(DBHelper.PAYMENT_EVENT_AMOUNT, amount);
        values.put(DBHelper.PAYMENT_EVENT_IS_PAYED, isPayed);
        values.put(DBHelper.PAYMENT_EVENT_FOR_DATE, date);
        values.put(DBHelper.PAYMENT_EVENT_FOR_TIME, time);

        db.insert(DBHelper.PAYMENT_EVENT_NAME, null, values);
    }

    public void addTODOData(int userID, String name, String description){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.USERS_UID, userID);
        values.put(DBHelper.TODO_EVENT_NAME, name);
        values.put(DBHelper.TODO_EVENT_DESCRIPTION, description);

        db.insert(DBHelper.TABLE_NAME_TODOS, null, values);
    }

    public void addShoppingListData(String itemName, double itemValue, int listID, int userID){
        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBHelper.SHOPPING_LIST_ITEM_NAME, itemName);
        values.put(DBHelper.SHOPPING_LIST_ITEM_VALUE, itemValue);
        values.put(DBHelper.SHOPPING_LIST_LIST_ID, listID);
        values.put(DBHelper.USERS_UID, userID);

        db.insert(DBHelper.TABLE_NAME_SHOPPING_LISTS, null, values);
    }

    class DBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "lo";
        private static final int DATABASE_VERSION = 1;

        private static final String USERS_TABLE_NAME = "lo.users";
        private static final String USERS_UID = "user_id";
        private static final String USERS_TABLE_USERNAME_COLUMN = "username";
        private static final String USERS_TABLE_PASSWORD_COLUMN = "password";
        private static final String USERS_TABLE_EMAIL_COLUMN = "email";

        private static final String PAYMENT_EVENTS_TABLE_NAME = "lo.payment_events";
        private static final String PAYMENT_EVENTS_ID = "pe_id";
        private static final String PAYMENT_EVENT_NAME = "pe_name";

        private static final String PAYMENT_EVENT_DESCRIPTION = "description";
        private static final String PAYMENT_EVENT_AMOUNT = "ammount";
        private static final String PAYMENT_EVENT_IS_PAYED = "is_payed";
        private static final String PAYMENT_EVENT_FOR_DATE = "for_date";
        private static final String PAYMENT_EVENT_FOR_TIME = "for_time";

        private static final String TABLE_NAME_TODOS = "lo.todos";
        private static final String TODO_EVENTS_ID = "pe_id";
        private static final String TODO_EVENT_NAME = "pe_name";
        private static final String TODO_EVENT_DESCRIPTION = "description";

        private static final String TABLE_NAME_SHOPPING_LISTS = "lo.shopping_lists";
        private static final String SHOPPING_LIST_ITEM_ID = "pe_id";
        private static final String SHOPPING_LIST_ITEM_NAME = "item_name";
        private static final String SHOPPING_LIST_ITEM_VALUE = "item_value";
        private static final String SHOPPING_LIST_LIST_ID = "list_id";

        private static final String NOTIFICATION_EVENTS_TABLE_NAME = "lo.notification_events";
        private static final String NOTIFICATION_EVENTS_ID = "ne_id";
        private static final String NOTIFICATION_EVENTS_NAME = "ne_name";
        private static final String NOTIFICATION_EVENTS_DESCRIPTION = "description";
        private static final String NOTIFICATION_EVENTS_DATE = "for_date";
        private static final String NOTIFICATION_EVENTS_TIME = "for_time";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " ("
                    + USERS_UID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + USERS_TABLE_USERNAME_COLUMN + " VARCHAR(25) UNIQUE NOT NULL, " +
                    USERS_TABLE_PASSWORD_COLUMN + " VARCHAR(255) NOT NULL, " +
                    USERS_TABLE_EMAIL_COLUMN + " VARCHAR(25) UNIQUE NOT NULL" +
                    ");");

            db.execSQL("CREATE TABLE " + PAYMENT_EVENTS_TABLE_NAME + " (" +
                    PAYMENT_EVENTS_ID + " int PRIMARY KEY AUTOINCREMENT," +
                    DBHelper.USERS_UID + " int NOT NULL," +
                    PAYMENT_EVENT_NAME + " VARCHAR(25) NOT NULL," +
                    PAYMENT_EVENT_DESCRIPTION + " VARCHAR(255), " +
                    PAYMENT_EVENT_AMOUNT + " DOUBLE PRECISION UNSIGNED," +
                    PAYMENT_EVENT_IS_PAYED + " BOOLEAN NOT NULL," +
                    PAYMENT_EVENT_FOR_DATE + " DATE NOT NULL," +
                    PAYMENT_EVENT_FOR_TIME + " TIME NOT NULL," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + TABLE_NAME_TODOS + " (" +
                    TODO_EVENTS_ID + " int PRIMARY KEY AUTO_INCREMENT," +
                    DBHelper.USERS_UID + " int NOT NULL," +
                    TODO_EVENT_NAME + " VARCHAR(25) NOT NULL," +
                    TODO_EVENT_DESCRIPTION + " VARCHAR(255)," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + TABLE_NAME_SHOPPING_LISTS + " (" +
                    SHOPPING_LIST_ITEM_ID + " int PRIMARY KEY AUTO_INCREMENT," +
                    SHOPPING_LIST_LIST_ID + " int NOT NULL," +
                    DBHelper.USERS_UID + " int NOT NULL," +
                    SHOPPING_LIST_ITEM_NAME + " VARCHAR(100) NOT NULL," +
                    SHOPPING_LIST_ITEM_VALUE + " DOUBLE PRECISION," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");

            db.execSQL("CREATE TABLE " + NOTIFICATION_EVENTS_TABLE_NAME + " (" +
                    NOTIFICATION_EVENTS_ID + " int PRIMARY KEY AUTO_INCREMENT," +
                    DBHelper.USERS_UID + " int NOT NULL," +
                    NOTIFICATION_EVENTS_NAME + " VARCHAR(25) NOT NULL," +
                    NOTIFICATION_EVENTS_DESCRIPTION + " VARCHAR(255), " +
                    NOTIFICATION_EVENTS_DATE + " DATE NOT NULL," +
                    NOTIFICATION_EVENTS_TIME + " TIME NOT NULL," +
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
