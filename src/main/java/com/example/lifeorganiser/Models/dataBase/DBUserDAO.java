package models.dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.dao.IUserDAO;
import models.user.User;

public class DBUserDAO implements IUserDAO {

	private List<User> allUsers = getAllUsers();
	
	@Override
	public void addUser(User user) {
		PreparedStatement st = null;
		try {
			st = DBManager.getInstance().getConnection().prepareStatement("INSERT INTO " + DBManager.DB_NAME + "." + DBManager.ColumnNames.USERS.toString().toLowerCase() + " (username, pass, email) VALUES ( ? , ? , ? )" );
			st.setString(1, user.getUserName());
			st.setString(2, user.getPassword());
			st.setString(3, user.getEmail());
			st.execute();
		
		} catch (SQLException e) {
			System.out.println("Error executing the statement in addUser: " + e.getMessage());
		} finally {
			try {
				st.close();
			} catch (SQLException e) {}
		}
	}

	@Override
	public List<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		
		Statement st = null;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT username, pass, email FROM " + DBManager.DB_NAME + "." + DBManager.ColumnNames.USERS.toString().toLowerCase());
			
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("pass");
				String email = rs.getString("email");
				User newUser = new User(username, password, email);
				users.add(newUser);
			}
			
		} catch (SQLException e) {
			System.out.println("Error executing the statement in getAllUsers: " + e.getMessage());
		} finally {
			try {
				st.close();
			} catch (SQLException e) {}
		}
		
		return users;
	}

	@Override
	public void registerUser(String username, String password, String email) {

		if(checkIfUserExists(username)) {
			System.out.println("The username is already taken.");
			return;
		}
		
		User newUser = new User(username, password, email);
		this.allUsers.add(newUser); // adds user to cache
		addUser(newUser); // adds user to DB	
	}

	@Override
	public boolean checkIfUserExists(String username) {
		for(User u : this.allUsers) {
			if (u.getUserName().equals(username)) {
				return true;
			}
		}
		return false;
	}

}
