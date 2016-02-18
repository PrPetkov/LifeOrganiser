package models;

import java.util.ArrayList;
import java.util.List;

import models.events.TODOEvent;
import models.events.Event;
import models.events.PaymentEvent;
import models.events.ShoppingList;

public class User {
	// private Database db;

	private String userName;
	private String password;

	private List<PaymentEvent> events;
	private List<TODOEvent> todos;
	private List<ShoppingList> shoppingList;
	// private List<Account> accounts;

	private static User user;

	// Money money;

	private User() {
		this.events = new ArrayList<>();
		// this.accounts = new ArrayList<>();
		this.todos = new ArrayList<>();
		// this.db = new Database(this);
	}

	// methods
	
	/*-------------PAYMENT EVENT-------------*/
	// TODO
	public void createPaymentEvent() {

	}
	
	// TODO
	public void modifyPaymentEvent(PaymentEvent event) {
		
	}

	// TODO
	public void removePaymentEvent(PaymentEvent event) {
		
	}
	
	public void pay(PaymentEvent event) {
		
	}
	
	/*------------TODO EVENT------------*/	
	// TODO
	public void createTODO() {

	}
	
	// TODO
	public void modifyTODO(TODOEvent todo) {
		
	}
	
	// TODO
	public void removeTODO(TODOEvent todo) {
		
	}
	
	/*--------------SHOPPING LIST EVENT---------------*/
	// TODO
	public void createShoppingList() {

	}
	
	// TODO
	public void modifyShoppingList(ShoppingList list) {
		
	}
	
	// TODO
	public void removeShoppingList(ShoppingList list) {
		
	}

	// TODO
	public void payShoppingList() {
		
	}
	/*------------------------------------*/
	
	// TODO
	/*
	 * SINGLETON MANAGER CLASS GETS USER private User(String userName) { this();
	 * setUserName(userName); setPassword(password); }
	 * 
	 * public static User getUser(){ if (User.user == null){ User.user = new
	 * User(); }
	 * 
	 * return User.user; }
	 * 
	 * public static boolean isValidUser(String username, String password) { if
	 * (username == null || password == null){ return false; }
	 * 
	 * //TODO check in the database for the user return true; }
	 * 
	 * public void downloadInfoFromDb(){ //TODO }
	 * 
	 * public void uploadInfoToDb(){ //TODO }
	 * 
	 */

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

	public Iterable<PaymentEvent> getEvents() {
		return events;
	}

	// public Iterable<Account> getAccounts() {
	// return accounts;
	// }

	public Iterable<TODOEvent> getTodos() {
		return todos;
	}

	public void addPaymentEvent(PaymentEvent event) {
		this.events.add(event);
	}

	// public void addAccount(Account account){
	// this.accounts.add(account);
	// }

	public void addTODO(TODOEvent todo) {
		this.todos.add(todo);
	}

}
