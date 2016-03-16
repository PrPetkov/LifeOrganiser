package com.example.lifeorganiser.src.Models.user;

import com.example.lifeorganiser.src.Models.accounts.Account;
import com.example.lifeorganiser.src.Models.accounts.DebitAccount;
import com.example.lifeorganiser.src.Models.events.DatedEvent;
import com.example.lifeorganiser.src.Models.events.Event;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.events.ShoppingList;
import com.example.lifeorganiser.src.Models.events.TODOEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class User {

	private String userName;
	private String password;
    private int uniqueDBId;
	private String email;

	private TreeSet<DatedEvent> events;
	private HashMap<TODOEvent.Type, ArrayList<TODOEvent>> todos;//type -> events
	private ArrayList<ShoppingList> shoppingList;
	private ArrayList<DebitAccount> debitAccounts;

	public User(String userName, String password, int uniqueDBId, String email) {
        this.setUserName(userName);
        this.setPassword(password);
        this.uniqueDBId = uniqueDBId;
        this.email = email;

		this.debitAccounts = new ArrayList<>();
		this.todos = new HashMap<>();
		this.shoppingList = new ArrayList<>();

        this.events = new TreeSet<DatedEvent>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((DatedEvent)o1).getDateTime().compareTo(((DatedEvent)o2).getDateTime());
            }
        });
	}

	// methods
	
	/*-------------PAYMENT EVENT-------------*/
	void addPaymentEvent(PaymentEvent event) {
        this.events.add(event);
	}


	void removePaymentEvent(PaymentEvent event) {
		this.events.remove(event);
	}
	
	/*--------------SHOPPING LIST EVENT---------------*/

	void addShoppingList(ShoppingList shoppingList) {
        this.shoppingList.add(shoppingList);
	}

	void removeShoppingList(ShoppingList list) {
		this.shoppingList.remove(list);
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

	public TreeSet<DatedEvent> getEvents() {
		return events;
	}

	public void addTODO(TODOEvent.Type type, TODOEvent event){
		if (!this.todos.containsKey(type)){
            this.todos.put(type, new ArrayList<TODOEvent>());
        }

        this.todos.get(type).add(event);
	}

    public ArrayList<TODOEvent> getTodos(TODOEvent.Type type){
        if (!this.todos.containsKey(type)){
            this.todos.put(type, new ArrayList<TODOEvent>());
        }

        return this.todos.get(type);
    }

	public void addEvent(DatedEvent event){
        if (event == null){
            return;
        }

        this.events.add(event);
    }

    public int getId(){
        return this.uniqueDBId;
    }

    public ArrayList<DebitAccount> getDebitAccounts(){
        return this.debitAccounts;
    }

    public void addDebitAccount(DebitAccount account){
        this.debitAccounts.add(account);
    }

	public ArrayList<ShoppingList> getAllShoppingLists(){
        return this.shoppingList;
    }
}
