package models.dataBase;

import models.interfaces.IDBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager implements IDBManager {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
	
	static final String USER = "orgos";
	static final String PASS = "admin";
	

	static Connection conn = null;
	static Statement stmt = null;
	
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
		
	private static void addTablesToSchema(Connection conn) {
		
		try {	
			PreparedStatement statement;
			String users = "CREATE TABLE lo.users (user_id int PRIMARY KEY AUTO_INCREMENT, username VARCHAR(25) UNIQUE NOT NULL, pass VARCHAR(25) NOT NULL, email VARCHAR(25) UNIQUE NOT NULL)";
			statement = conn.prepareStatement(users);
			statement.executeUpdate(users);
			System.out.println("Table created: users");
			
			String payment_events = "CREATE TABLE lo.payment_events (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), ammount DOUBLE PRECISION UNSIGNED, is_payed BOOLEAN NOT NULL, for_date DATE NOT NULL, for_time TIME NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			statement = conn.prepareStatement(payment_events);
			statement.executeUpdate(payment_events);
			System.out.println("Table created: payment_events");
			
			String todos = "CREATE TABLE lo.todos (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			statement = conn.prepareStatement(todos);
			statement.executeUpdate(todos);
			System.out.println("Table created: todos");
			
			String notification_events = "CREATE TABLE lo.notification_events (ne_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, ne_name VARCHAR(25) NOT NULL, description VARCHAR(255), for_date DATE NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			statement = conn.prepareStatement(notification_events);
			statement.executeUpdate(notification_events);
			System.out.println("Table created: notification_events");
			
			String shopping_lists = "CREATE TABLE lo.shopping_lists (pe_id int PRIMARY KEY AUTO_INCREMENT, list_id int NOT NULL, list_name VARCHAR(25) NOT NULL, item_name VARCHAR(100) NOT NULL, item_value DOUBLE PRECISION, user_id int NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			statement = conn.prepareStatement(shopping_lists);
			statement.executeUpdate(shopping_lists);
			System.out.println("Table created: shopping_lists");
			
			System.out.println("All tables created succesfully");
		} catch (SQLException e) {
			System.out.println("Creation of tables failed!");
			e.printStackTrace();
		}
	}

	private static void createSchema(Connection conn) {
		try {
			String createSchema = "CREATE SCHEMA `lo` DEFAULT CHARACTER SET utf8";
			PreparedStatement statement = conn.prepareStatement(createSchema);
			statement.executeUpdate(createSchema);
			System.out.println("Schema created successfully.");
			
		} catch (SQLException e) {
			System.out.println("Creation of schema failed!");
		}	
	}
}
