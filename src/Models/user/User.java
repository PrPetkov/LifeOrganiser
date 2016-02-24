package models.user;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.events.TODOEvent;
import models.exceptions.IllegalAmountException;
import models.exceptions.IncorrectInputException;
import models.exceptions.InsufficientAmountOfClientException;
import models.exceptions.NotExistException;
import models.accounts.Account;
import models.events.NotificationEvent;
import models.events.PaymentEvent;
import models.events.ShoppingList;

public class User {

	private String userName;
	private String password;
	private String email;
	private int uniqueID;

	private List<PaymentEvent> events;
	private List<TODOEvent> todos;
	private List<ShoppingList> shoppingList;
	private List<Account> accounts;
	private List<NotificationEvent> notifications;
	
	private static User user;
	private BigDecimal money;

	// calls the constructor when getting the users from the DB
	public User(String userName, int uniqueID, String password, String email) {
		this.setUserName(userName);
		this.setPassword(password);
		this.uniqueID = uniqueID;
		this.email = email;

		this.events = new ArrayList<>();
		this.accounts = new ArrayList<>();
		this.todos = new ArrayList<>();
		this.shoppingList = new ArrayList<>();
		this.notifications = new ArrayList<>();
	}
	
	// calls the constructor when registering new user
	public User(String userName, String password, String email) {
		this.setUserName(userName);
		this.setPassword(password);
		this.email = email;
	}

	// methods
	/*-------------PAYMENT EVENT-------------*/
	public void createPaymentEvent(String eventTitle, String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException {
		/*
		 * amount is checked in the OOP in the PaymentEvent class 
		 * isIncome will be button (radio?) and will be a must to continue - no need for check
		 * in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP forDate will be chooser from
		 * the calendar and and will be a must to continue - no need for check in the OOP
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
		 * isIncome will be button (radio?) and will be a must to continue - no need for check
		 * in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP forDate will be chooser from
		 * the calendar and and will be a must to continue - no need for check in the OOP
		 */
		
		if (event == null) {
			try {
				throw new NotExistException("Must select Payment Event to edit!");
			} catch (NotExistException e) {}
		}
		
		if (eventTitle == null || eventTitle.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {
			}
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
				throw new NotExistException("Must select payment event to remove!");
			} catch (NotExistException e) {}
		}
		
		this.events.remove(event);
	}

	public void pay(PaymentEvent event) {
		if (event == null) {
			try {
				throw new NotExistException("Must select payment event to pay!");
			} catch (NotExistException e) {}
		}

		payEvent(event.getAmount());
		event.setPaid(true);
	}

	private void payEvent(double amount) {
		double temp = this.money.doubleValue();
		if (temp < amount) {
			try {
				throw new InsufficientAmountOfClientException("Not enough money in the account!");
			} catch (InsufficientAmountOfClientException e) {}
		}

		BigDecimal tempDecimal = new BigDecimal(amount);
		this.money.subtract(tempDecimal);
	}

	/*------------TODO EVENT------------*/
	public void createTODO(String name, String description) {
		/*
		 * isIncome will be button (radio?) and will be a must to continue - no
		 * need for check in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP
		 */
		if (name == null || name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		this.todos.add(new TODOEvent(name, description));
	}

	public void modifyTODO(TODOEvent todo, String name, String description) {
		/*
		 * isIncome will be button (radio?) and will be a must to continue - no
		 * need for check in the OOP isPayed will be button (radio?) and will be a must to continue - no need for check in the OOP
		 */
		if (todo == null) {
			try {
				throw new NotExistException("Must select TODO to edit!");
			} catch (NotExistException e) {}
		}
		
		if (name == null || name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		todo.setTitle(name);
		todo.setDescription(description);
	}

	public void removeTODO(TODOEvent todo) {
		if (todo == null) {
			try {
				throw new NotExistException("Must select TODO to remove!");
			} catch (NotExistException e) {}
		}
		this.todos.remove(todo);
	}

	/*--------------SHOPPING LIST EVENT---------------*/
	public void createShoppingList(String name) {
		if (name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("You cannot create a shopping list without a name!");
			} catch (IncorrectInputException e) {}
		}
		
		this.shoppingList.add(new ShoppingList(name));
	}

	public void modifyShoppingList(ShoppingList entry, String name) {
		if (entry == null) {
			try {
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {}
		}
		
		if (name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("You cannot edit a shopping list and leave it without a name!");
			} catch (IncorrectInputException e) {}
		}
		
		entry.setName(name);
	}

	public void removeShoppingList(ShoppingList entry) {
		if (entry == null) {
			try {
				throw new NotExistException("Must select a shopping list to remove!");
			} catch (NotExistException e) {}
		}
		this.notifications.remove(entry);
	}
	
	public void addItemToShoppingList(ShoppingList list, String itemName) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to add item in it!");
			} catch (NotExistException e) {}
		}
		
		if (itemName.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input item name must not be empty!");
			} catch (IncorrectInputException e) {}
		}
		
		list.addEntry(itemName);
	}
	
	public void addItemToShoppingList(ShoppingList list, String itemName, double price) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to add item in it!");
			} catch (NotExistException e) {}
		}
		
		if (itemName.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input item name must not be empty!");
			} catch (IncorrectInputException e) {}
		}
		
		try {
			list.addEntry(itemName, price);
		} catch (IllegalAmountException e) {}
	}
	
	public void removeItemFromShoppingList(ShoppingList list, String itemName) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {}
		}
		
		if (!list.getEntries().containsKey(itemName)) {
			try {
				throw new IncorrectInputException("The item does not exist in the shopping list!");
			} catch (IncorrectInputException e) {}
		}
		
		list.getEntries().get(itemName);
	}
		
	public void payShoppingList(ShoppingList list, double amount) {
		if (list == null) {
			try {
				throw new NotExistException("Must select ShoppingList to edit!");
			} catch (NotExistException e) {}
		}
		
		double temp = this.money.doubleValue();
		if (temp < amount) {
			try {
				throw new InsufficientAmountOfClientException("Not enough money in the account!");
			} catch (InsufficientAmountOfClientException e) {}
		}

		BigDecimal tempDecimal = new BigDecimal(amount);
		this.money.subtract(tempDecimal);
		
	}
	
	/*--------------NOTIFICATION EVENTS LIST--------------*/
	
	public void createNotificationEvent(String name, String description, LocalDate forDate) {
		/*
		 * the calendar and and will be a must to continue - no need for check in the OOP
		 */
		if (name == null || name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		this.notifications.add(new NotificationEvent(name, description, forDate));
	}

	public void modifyNotificationEvent(NotificationEvent event, String name, String description, LocalDate forDate) {
		/*
		 * the calendar and and will be a must to continue - no need for check in the OOP
		 */
		if (event == null) {
			try {
				throw new NotExistException("Must select notification to edit!");
			} catch (NotExistException e) {}
		}
		
		if (name == null || name.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input name must not be empty!");
			} catch (IncorrectInputException e) {}
		} else if (description == null || description.trim().isEmpty()) {
			try {
				throw new IncorrectInputException("The input description must not be empty!");
			} catch (IncorrectInputException e) {}
		}

		event.setTitle(name);
		event.setDescription(description);
		event.setForDate(forDate);
	}

	public void removeNotificationEvent(NotificationEvent event) {
		if (event == null) {
			try {
				throw new NotExistException("Must select a notification event to remove!");
			} catch (NotExistException e) {}
		}
		this.notifications.remove(event);
	}
	
	/*------------------------------------*/

	// getters and setters
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

	public Iterable<Account> getAccounts() {
		return accounts;
	}

	public Iterable<TODOEvent> getTodos() {
		return todos;
	}

	public void addPaymentEvent(PaymentEvent event) {
		this.events.add(event);
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public void addTODO(TODOEvent todo) {
		this.todos.add(todo);
	}

	public String getEmail() {
		return email;
	}

	public int getUniqueDBId() {
		return uniqueID;
	}

	public List<ShoppingList> getShoppingList() {
		return shoppingList;
	}

	public List<NotificationEvent> getNotifications() {
		return notifications;
	}

}
