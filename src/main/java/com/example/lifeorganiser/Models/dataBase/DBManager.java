package models.dataBase;
import models.interfaces.IDBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager implements IDBManager {

	enum ColumnNames {USERS, PAYMENT_EVENTS, TODOS, NOTIFICATION_EVENTS, SHOPPING_LISTS, SHOPPING_ENTRIES}

	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
	
	final static String USER = "orgos";
	final static String PASS = "admin";
	final static String DB_NAME = "lo"; // Date Base name

	Connection conn = null;
	Statement stmt = null;
	
	private static DBManager instance = null;
	
	private DBManager() {
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver loaded successfully");
		} catch (ClassNotFoundException e) {
			System.out.println("No such driver imported");
		}
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection to database was successfully");
			
		} catch (SQLException e) {
			System.out.println("Something went wrong with the connection to the database: "  + e.getMessage());
		}
		
		createSchema(conn);
		addTablesToSchema(conn);
		
		try {
			if(conn != null) {
				conn.close();
				System.out.println("connection closed");
			}
		} catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	public static DBManager getInstance() {
		if(instance == null) {
	         instance = new DBManager();
	      }
	      return instance;
	}
		
	private void addTablesToSchema(Connection conn) {
		
		PreparedStatement statement = null;
		
		String users = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + ColumnNames.USERS.toString().toLowerCase() + " (user_id int PRIMARY KEY AUTO_INCREMENT, username VARCHAR(25) UNIQUE NOT NULL, pass VARCHAR(25) NOT NULL, email VARCHAR(25) UNIQUE NOT NULL)";
		String payment_events = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + ColumnNames.PAYMENT_EVENTS.toString().toLowerCase() +  " (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), ammount DOUBLE PRECISION UNSIGNED, is_payed BOOLEAN NOT NULL, for_date DATE NOT NULL, for_time TIME NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
		String todos = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + ColumnNames.TODOS.toString().toLowerCase() +  " (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
		String notification_events = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + ColumnNames.NOTIFICATION_EVENTS.toString().toLowerCase() +  " (ne_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, ne_name VARCHAR(25) NOT NULL, description VARCHAR(255), for_date DATE NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
		String shopping_lists = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + ColumnNames.SHOPPING_LISTS.toString().toLowerCase() +  " (pe_id int PRIMARY KEY AUTO_INCREMENT, list_id int NOT NULL UNIQUE, list_name VARCHAR(25) NOT NULL, user_id int NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
		String shopping_entries = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + ColumnNames.SHOPPING_ENTRIES.toString().toLowerCase() +  " (pe_id int PRIMARY KEY AUTO_INCREMENT, item_name VARCHAR(100) NOT NULL, item_value DOUBLE PRECISION, list_id int NOT NULL, FOREIGN KEY (list_id) REFERENCES lo.shopping_lists(list_id))";
		
		createTable(conn, statement, users);
		createTable(conn, statement, payment_events);
		createTable(conn, statement, todos);
		createTable(conn, statement, notification_events);
		createTable(conn, statement, shopping_lists);
		createTable(conn, statement, shopping_entries);
					
		System.out.println("All tables loaded successfully!");
	}

	private void createTable(Connection conn, PreparedStatement statement, String users) {
		
		try {
			statement = conn.prepareStatement(users);
			statement.executeUpdate(users);
			
		} catch (SQLException e) {
			System.out.println("Creation of tables failed: " + e.getMessage());
		}
	}

	private void createSchema(Connection conn) {
		try {			
			String createSchema = "CREATE SCHEMA IF NOT EXISTS `lo` DEFAULT CHARACTER SET utf8";
			PreparedStatement statement = conn.prepareStatement(createSchema);
			statement.executeUpdate(createSchema);
			System.out.println("Schema created successfully.");
			
		} catch (SQLException e) {
			System.out.println("Creation of schema failed: " + e.getMessage());
		}	
	}

	public Connection getConnection() {
		return conn;
	}

}
