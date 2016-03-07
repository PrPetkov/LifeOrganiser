package com.example.lifeorganiser.src.Models.events;

import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingList {

	private boolean isPaid;
	private double amount;
	private HashMap<String, Double> entries;

	public ShoppingList() {
		this.amount = 0;
		this.isPaid = false;
		this.entries = new HashMap<String, Double>();
	}

	// methods
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

	HashMap<String, Double> getEntries() {
		return entries;
	}

}
