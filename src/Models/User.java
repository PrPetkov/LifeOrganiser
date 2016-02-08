package Models;


import Interfaces.ITODO;
import Models.accounts.Account;
import Models.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Database db;

    private String userName;
    private String password;
    
    private List<Task> tasks;
    private List<Account> accounts;
    private List<ITODO> todos;

    private static User user;
    
//    Money money;


    private User() {
        this.tasks = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.todos = new ArrayList<>();
        this.db = new Database(this);
    }

    private User(String userName) {
		this();
		setUserName(userName);
//		setPassword(password);
	}

    public static User getUser(){
        if (User.user == null){
            User.user = new User();
        }

        return User.user;
    }

	private void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
    
	private void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public Iterable<Task> getTasks() {
		return tasks;
	}

	public Iterable<Account> getAccounts() {
		return accounts;
	}

	public Iterable<ITODO> getTodos() {
		return todos;
	}

	public void addTask(Task task){
		this.tasks.add(task);
	}

	public void addAccount(Account account){
		this.accounts.add(account);
	}

	public void addTODO(ITODO todo){
		this.todos.add(todo);
	}

	public static boolean isValidUser(String username, String password) {
        if (username == null || password == null){
            return false;
        }

		//TODO check in the database for the user
        return true;
	}

	public void downloadInfoFromDb(){
    //TODO
    }

    public void uploadInfoToDb(){
        //TODO
    }
}
