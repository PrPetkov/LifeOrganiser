package appObjects;


import appObjects.accounts.Account;
import appObjects.tasks.TODOTask;
import appObjects.tasks.Task;

import java.util.List;

public class User {

    static final String userName;
    private String password;
    
    private List<Task> tasks;
    private List<Account> accounts;
    private List<TODOTask> todos;
    
		
	User(String userName, String password) {
		setName(userName);
		setPassword(password);
	}
	
	default String setPassword(String password) {
		this.password = password;
	}
	
	default void getPassword() {
		return this.password;
	}
    
	private String setUserName(String userName) {
		this.userName = userName;
	}
	
	private void getUserName() {
		return this.userName;
	}
	
}
