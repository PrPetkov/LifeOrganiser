package models.dataBase;

import models.exceptions.NotExistException;
import models.interfaces.IDBManager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

enum ColumnNames {USERS, PAYMENT_EVENTS, TODOS, NOTIFICATION_EVENTS, SHOPPING_LISTS, SHOPPING_ENTRIES}
public class DBManager implements IDBManager {

	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
	
	final String USER = "orgos";
	final String PASS = "admin";
	final String dbName = "lo"; // Date Base name

	Connection conn = null;
	Statement stmt = null;
	
	private DBManager instance = null;
	
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
	
	public DBManager getInstance() {
		if(instance == null) {
	         instance = new DBManager();
	      }
	      return instance;
	}
		
	private void addTablesToSchema(Connection conn) {
		
		try {	
			PreparedStatement statement;
			
			if(doesTableExists(conn, ColumnNames.USERS.toString())) {
				String users = "CREATE TABLE lo.users (user_id int PRIMARY KEY AUTO_INCREMENT, username VARCHAR(25) UNIQUE NOT NULL, pass VARCHAR(25) NOT NULL, email VARCHAR(25) UNIQUE NOT NULL)";
				statement = conn.prepareStatement(users);
				statement.executeUpdate(users);
				System.out.println("Table created: users");
			} else {
				try {
					throw new NotExistException("Table: " + ColumnNames.USERS.toString().toLowerCase() + " already exists!");
				} catch (NotExistException e) {}
			}
			
			if(doesTableExists(conn, ColumnNames.PAYMENT_EVENTS.toString())) {
				String payment_events = "CREATE TABLE lo.payment_events (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), ammount DOUBLE PRECISION UNSIGNED, is_payed BOOLEAN NOT NULL, for_date DATE NOT NULL, for_time TIME NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
				statement = conn.prepareStatement(payment_events);
				statement.executeUpdate(payment_events);
				System.out.println("Table created: payment_events");
			} else {
				try {
					throw new NotExistException("Table: " + ColumnNames.PAYMENT_EVENTS.toString().toLowerCase() + " already exists!");
				} catch (NotExistException e) {}
			}
			
			if(doesTableExists(conn, ColumnNames.TODOS.toString())) {
				String todos = "CREATE TABLE lo.todos (pe_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, pe_name VARCHAR(25) NOT NULL, description VARCHAR(255), FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
				statement = conn.prepareStatement(todos);
				statement.executeUpdate(todos);
				System.out.println("Table created: todos");
			} else {
				try {
					throw new NotExistException("Table: " + ColumnNames.TODOS.toString().toLowerCase() + " already exists!");
				} catch (NotExistException e) {}
			}
			
			if(doesTableExists(conn, ColumnNames.NOTIFICATION_EVENTS.toString())) {
				String notification_events = "CREATE TABLE lo.notification_events (ne_id int PRIMARY KEY AUTO_INCREMENT, user_id int NOT NULL, ne_name VARCHAR(25) NOT NULL, description VARCHAR(255), for_date DATE NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
				statement = conn.prepareStatement(notification_events);
				statement.executeUpdate(notification_events);
				System.out.println("Table created: notification_events");
			} else {
				try {
					throw new NotExistException("Table: " + ColumnNames.NOTIFICATION_EVENTS.toString().toLowerCase() + " already exists!");
				} catch (NotExistException e) {}
			}
			
			if(doesTableExists(conn, ColumnNames.SHOPPING_LISTS.toString())) {
				String shopping_lists = "CREATE TABLE lo.shopping_lists (pe_id int PRIMARY KEY AUTO_INCREMENT, list_id int NOT NULL UNIQUE, list_name VARCHAR(25) NOT NULL, user_id int NOT NULL, FOREIGN KEY (user_id) REFERENCES lo.users(user_id))";
				statement = conn.prepareStatement(shopping_lists);
				statement.executeUpdate(shopping_lists);
				System.out.println("Table created: shopping_lists");
			} else {
				try {
					throw new NotExistException("Table: " + ColumnNames.SHOPPING_LISTS.toString().toLowerCase() + " already exists!");
				} catch (NotExistException e) {}
			}
			
			if(doesTableExists(conn, ColumnNames.SHOPPING_ENTRIES.toString())) {
				String shopping_entries = "CREATE TABLE lo.shopping_entries (pe_id int PRIMARY KEY AUTO_INCREMENT, item_name VARCHAR(100) NOT NULL, item_value DOUBLE PRECISION, list_id int NOT NULL, FOREIGN KEY (list_id) REFERENCES lo.shopping_lists(list_id))";
				statement = conn.prepareStatement(shopping_entries);
				statement.executeUpdate(shopping_entries);
				System.out.println("Table created: shopping_entries");
			} else {
				try {
					throw new NotExistException("Table: " + ColumnNames.SHOPPING_ENTRIES.toString().toLowerCase() + " already exists!");
				} catch (NotExistException e) {}
			}
				
		} catch (SQLException e) {
			System.out.println("Creation of tables failed!");
			e.printStackTrace();
		}
	}

	private boolean doesTableExists(Connection conn, String columnName) {
			
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getTables(null, null, columnName, null);
			
			if(resultSet.next()) {
				return false;
			}
			
		} catch (SQLException e) {
			try {
				throw new NotExistException("Error on creating: " + columnName);
			} catch (NotExistException e1) {}
		} 		
	
		return true;
	}

	private void createSchema(Connection conn) {
		try {
			
			if(doesSchemaExists(conn)) {
				System.out.println("Schema already exists!");
				return;
			}
			
			String createSchema = "CREATE SCHEMA `lo` DEFAULT CHARACTER SET utf8";
			PreparedStatement statement = conn.prepareStatement(createSchema);
			statement.executeUpdate(createSchema);
			System.out.println("Schema created successfully.");
			
		} catch (SQLException e) {
			System.out.println("Creation of schema failed!");
		}	
	}

	private boolean doesSchemaExists(Connection conn) {
		
		ResultSet resultSet = null;
		
		try {
			resultSet = conn.getMetaData().getCatalogs();
		} catch (SQLException e) {}
		
		 try {
			while (resultSet.next()) {
			      String databaseName = resultSet.getString(1);
			        if(databaseName.equalsIgnoreCase(dbName)){
			        	
			            return true;
			        }
			    }
		} catch (SQLException e) {
			System.out.println("Error on result set.");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {}
		}

		return false;
	}
}
