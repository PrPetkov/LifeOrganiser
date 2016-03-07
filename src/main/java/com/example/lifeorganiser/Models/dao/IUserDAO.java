package models.dao;

import java.util.List;
import models.user.User;

public interface IUserDAO {
	
	void addUser(User user);
	List<User> getAllUsers();
	void registerUser(String username, String password, String email);
	boolean checkIfUserExists(String username);
}
