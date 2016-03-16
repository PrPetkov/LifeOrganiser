package com.example.lifeorganiser.src.Models.events;

import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingList implements Serializable {

	private boolean isPaid;
	private String name;
	private int dbID;
	private ArrayList<ShoppingListEntry> shoppingEntries;

	public ShoppingList(String name, int dbID, boolean isPaid) {
		this.name = name;
		this.dbID = dbID;
		this.isPaid = isPaid;
		this.shoppingEntries = new ArrayList<>();
	}

	// methods
	public void addEntry(ShoppingListEntry entry) {
		if (setEntryAmount(entry.getPrice())) {
			this.shoppingEntries.add(entry);
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

	public double getAmountOfAllEntries() {
		double currentAmountOfList = 0;

		for (ShoppingListEntry entry : this.shoppingEntries){
            currentAmountOfList += entry.getPrice();
        }
		
		return currentAmountOfList;
	}

	public double getAmountOfAllTakenEnties(){
		double currentAmountOfList = 0;

		for (ShoppingListEntry entry : this.shoppingEntries){
			if (entry.isTaken()) {
				currentAmountOfList += entry.getPrice();
			}
		}

		return currentAmountOfList;
	}
	
	// getters and setters
	boolean getIsPaid() {
		return this.isPaid;
	}

	ArrayList<ShoppingListEntry> getShoppingEntries() {
		return shoppingEntries;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public int getDbID() {
		return dbID;
	}

	public String getName() {
		return name;
	}

	public ArrayList<ShoppingListEntry> getEntries(){
		return this.shoppingEntries;
	}
}
