package models.events;

import java.util.HashMap;
import java.util.Map;

import models.exceptions.IllegalAmountException;

public class ShoppingList {
	
	private String name;
	private boolean isPaid;
	private double amount;
	private HashMap<String, Double> entries;

	public ShoppingList(String name) {
		this.name = name;
		this.amount = 0;
		this.isPaid = false;
		this.entries = new HashMap<String, Double>();
	}

	// methods
	public void addEntry(String name){
		this.entries.put(name, amount);
	}
	
	public void addEntry(String name, Double amount) throws IllegalAmountException {
		if (setEntryAmount(amount)) {
			this.entries.put(name, amount);
		}
	}
	
	private boolean setEntryAmount(double amount) {
		try {
			if (amount < 0) {
				throw new IllegalAmountException("Invalid value of entry !");
			}
		} catch (IllegalAmountException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	public void removeEntry(String name) {
		if (this.entries.containsKey(name)) {
			this.entries.remove(name);
		}
	}

	public double getAmountOfAllEntries() {
		double currentAmountOfList = 0;
		
		for (Map.Entry<String, Double> map : this.entries.entrySet()) {
			if (map.getKey() == null) {
				continue;
			}
			
			currentAmountOfList += map.getValue();
		}
		
		return currentAmountOfList;
	}

	public void setAmountOfShoppingList(double amount) throws IllegalAmountException {
		if (this.amount > 0) {
			this.amount = amount;
		} else {
			throw new IllegalAmountException("The amount must be positive !");
		}
	}
	
	// getters and setters
	boolean getIsPaid() {
		return this.isPaid;
	}

	public HashMap<String, Double> getEntries() {
		return entries;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
