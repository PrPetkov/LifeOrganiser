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
	static final String TABLE_URL = "jdbc:mysql://localhost:3306/lo?useSSL=false";
	
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
		
		createSchema();
		addTablesToSchema();
	}
	
	public static DBManager getInstance() {
		if(instance == null) {
	         instance = new DBManager();
	      }
	      return instance;
	}
		
	private static void addTablesToSchema() {
		try {
			conn = DriverManager.getConnection(TABLE_URL, USER, PASS);
			System.out.println("connection to lo schema successfully");
			
		} catch (SQLException e) {
			System.out.println("Something went wrong with the connection to lo: "  + e.getMessage());
		}
		
		try {	
			PreparedStatement st;
			String users = "CREATE TABLE lo.users (user_id int PRIMARY KEY AUTO_INCREMENT, username VARCHAR(25) UNIQUE NOT NULL, pass VARCHAR(25) NOT NULL, email VARCHAR(25) UNIQUE NOT NULL)";
			st = conn.prepareStatement(users);
			st.executeUpdate(users);
			
			String payment_events = "CREATE TABLE lo.payment_events (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), ammount DOUBLE PRECISION UNSIGNED, is_payed BOOLEAN NOT NULL, for_date DATE NOT NULL, for_time TIME NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			st = conn.prepareStatement(payment_events);
			st.executeUpdate(payment_events);
			
			String todos = "CREATE TABLE lo.todos (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			st = conn.prepareStatement(todos);
			st.executeUpdate(todos);
			
			String notification_events = "CREATE TABLE lo.notification_events (ne_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, ne_name VARCHAR(25) NOT NULL, description VARCHAR(255), for_date DATE NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			st = conn.prepareStatement(notification_events);
			st.executeUpdate(notification_events);
			
			String shopping_lists = "CREATE TABLE lo.shopping_lists (pe_id int PRIMARY KEY AUTO_INCREMENT, list_id int NOT NULL, list_name VARCHAR(25) NOT NULL, item_name VARCHAR(100) NOT NULL, item_value DOUBLE PRECISION, user_id int NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
			st = conn.prepareStatement(shopping_lists);
			st.executeUpdate(shopping_lists);
			
			System.out.println("Tables created succesfully");
		} catch (SQLException e) {
			System.out.println("Creation of tables failed!");
			e.printStackTrace();
		}
		
		try {
			if(conn != null) {
				conn.close();
				System.out.println("connection closed");
			}
		} catch(SQLException se){
			se.printStackTrace();
		}
	}

	private static void createSchema() {
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("connection created successfully");
			
		} catch (SQLException e) {
			System.out.println("Something went wrong with the connection : "  + e.getMessage());
		}
		
		try {
			String createSchema = "CREATE SCHEMA `lo` DEFAULT CHARACTER SET utf8";
			PreparedStatement st = conn.prepareStatement(createSchema);
			st.executeUpdate(createSchema);
			
			System.out.println("Schema created successfully.");
		} catch (SQLException e) {
			System.out.println("Creation of schema failed!");
		}
		
		try {
			if(conn != null) {
				conn.close();
				System.out.println("connection closed");
			}
		} catch(SQLException se){
			se.printStackTrace();
		}
	
	}
}
