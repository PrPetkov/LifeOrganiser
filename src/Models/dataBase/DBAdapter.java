package Models.dataBase;


import Models.Interfaces.IDBManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

public class DBAdapter implements IDBManager {

    private DBHelper helper;

    public DBAdapter(Context context) {
        this.helper = new DBHelper(context);
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
        values.put(DBHelper.PAYMENT_EVENT_IS_OVERDUE, isOverDue);
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

    public void addShoppingListData(){
        //TODO
        SQLiteDatabase db = this.helper.getWritableDatabase();
//        SHOPPING_LIST_ID
//        DBHelper.USERS_UID
//        SHOPPING_LIST_ITEM_NAME
//        SHOPPING_LIST_ITEM_VALUE
        ContentValues values = new ContentValues();
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
        private static final String PAYMENT_EVENT_IS_OVERDUE = "is_overdue";
        private static final String PAYMENT_EVENT_FOR_DATE = "for_date";
        private static final String PAYMENT_EVENT_FOR_TIME = "for_time";

        private static final String TABLE_NAME_TODOS = "lo.todos";
        private static final String TODO_EVENTS_ID = "pe_id";
        private static final String TODO_EVENT_NAME = "pe_name";
        private static final String TODO_EVENT_DESCRIPTION = "description";

        private static final String TABLE_NAME_SHOPPING_LISTS = "lo.shopping_lists";
        private static final String SHOPPING_LIST_ID = "pe_id";
        private static final String SHOPPING_LIST_ITEM_NAME = "item_name";
        private static final String SHOPPING_LIST_ITEM_VALUE = "item_value";

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
                    PAYMENT_EVENT_IS_OVERDUE + " BOOLEAN NOT NULL," +
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
                    SHOPPING_LIST_ID + " int PRIMARY KEY AUTO_INCREMENT," +
                    DBHelper.USERS_UID + " int NOT NULL," +
                    SHOPPING_LIST_ITEM_NAME + " VARCHAR(100) NOT NULL," +
                    SHOPPING_LIST_ITEM_VALUE + " DOUBLE PRECISION," +
                    "FOREIGN KEY (" + DBHelper.USERS_UID + ") REFERENCES " + USERS_TABLE_NAME + "(" + DBHelper.USERS_UID + ")" +
                    ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST " + USERS_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXIST " + PAYMENT_EVENTS_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME_TODOS);
            db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME_SHOPPING_LISTS);

            this.onCreate(db);
        }
    }
}
