package Models.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.events.TODOEvent;
import models.exceptions.IllegalAmountException;
import models.exceptions.IncorrectInputException;
import models.exceptions.NotExistException;
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
	public void createPaymentEvent(String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException {
		/*
		 * amount is checked in the OOP in the PaymentEvent class
		 * isIncome will be button (radio?) and will be a must to continue - no need for check in the OOP
		 * isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP
		 * forDate will be chooser from the calendar and and will be a must to continue - no need for check in the OOP
		 */
		if (eventTitle == null || eventTitle.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		} 
		
		this.events.add(new PaymentEvent(eventTitle, description, amount, isIncome, isPaid, forDate));
	}
	
	public void modifyPaymentEvent(PaymentEvent event, String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) {
		/*
		 * amount is checked in the OOP in the PaymentEvent class
		 * isIncome will be button (radio?) and will be a must to continue - no need for check in the OOP
		 * isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP
		 * forDate will be chooser from the calendar and and will be a must to continue - no need for check in the OOP
		 */
		if (eventTitle == null || eventTitle.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		} 
		
		event.setTitle(eventTitle);
		event.setDescription(description);
		event.setAmount(amount);
		event.setIncome(isIncome);
		event.setPaid(isPaid);
		event.setForDate(forDate);
	}

	public void removePaymentEvent(PaymentEvent event) {
		if (event == null) {
			try {
				throw new NotExistException();
			} catch (NotExistException e) {}
		}
		this.events.remove(event);
	}
	
	// TODO
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
