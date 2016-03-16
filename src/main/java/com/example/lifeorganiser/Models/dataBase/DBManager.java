package com.example.lifeorganiser.src.Models.dataBase;

import com.example.lifeorganiser.src.Models.Interfaces.IDBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class DBManager implements IDBManager{

	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded successfully");
		} catch (ClassNotFoundException e) {
			System.out.println("No such driver imported");
		}
		
		//url = jdbc:mysql://<ip>:<port>/<dbName>;
		final String url = "jdbc:mysql://localhost:3306/hr?autoReconnect=true&useSSL=false";
		final String user = "orgos";
		final String pass = "admin";
		
		Connection con = null;
		
		try {
			con  = DriverManager.getConnection(url, user, pass);
			System.out.println("connection created successfully");
			
		} catch (SQLException e) {
			System.out.println("Something went wrong with the connection : "  + e.getMessage());
		}
		
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT department_name FROM hr.departments;");
			System.out.println("query successfull");
			
			boolean firstEntry = true;
			int counter = 1;
			while(rs.next()) {
				if (firstEntry) {
					System.out.println("Departments: " );
					firstEntry = false;
				}
				System.out.print(counter++ + ". ");
				System.out.print(rs.getString("department_name"));
				System.out.println();
			}
			
//			System.out.println("Updating!");
//			String name = "Pesho";
//			String family = "Peshev";
//			int age = new Random().nextInt(20) + 25;
//			int teacher_id = 4;
//			
//			String query = "INSERT INTO STUDENTS (name, family, age, teacher_id) VALUES ( ?, ?, ?, ?)";
//			Set<Product> products = new HashSet();
//			
//			for(Product s : products) {
//				PreparedStatement pst = con.prepareStatement(query);
//				pst.setString(1, s.getName());
//				pst.setString(2, family);
//				pst.setInt(3, age);
//				pst.setInt(4, teacher_id);
//				pst.executeUpdate();
//			}
			
//			daiPari(con, "Garo");
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Something went wrong with a statemtnt : "  + e.getMessage());
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void daiPari(Connection con, String from) throws SQLException {
		
		con.setAutoCommit(false);
		try{
			PreparedStatement st = con.prepareStatement("UPDATE STUDENTS SET balance = balance - 1000 WHERE name = ?");
			st.setString(1, from);
			st.executeQuery();
			
			st = con.prepareStatement("UPDATE STUDENTS SET balance = balance + 1000 WHERE name = ?");
			st.setString(1, "Krasi");
			st.executeQuery();
			
			con.commit();
		}
		catch(SQLException e) {
			con.rollback();
			throw e;
		}
		finally {
			con.setAutoCommit(true);
		}
		
		//update garo`s money to balance - 1000
		//updates my money to balance + 1000
		
	}
}
