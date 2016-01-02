package appObjects;


import Interfaces.ITODO;
import appObjects.accounts.Account;
import appObjects.tasks.TODOTask;
import appObjects.tasks.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private String password;
    
    private List<Task> tasks;
    private List<Account> accounts;
    private List<ITODO> todos;
    
//    Money money;
		
	User(String userName, String password) {


		this.tasks = new ArrayList<>();
		this.accounts = new ArrayList<>();
		this.todos = new ArrayList<>();

		setUserName(userName);
		setPassword(password);
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

	public BigDecimal calcMoney(){
		throw new NotImplementedException();
	}

}
