package com.example.lifeorganiser.src.Models.events;


import java.io.Serializable;

public class ShoppingListEntry implements Serializable {

    private String name;
    private double price;
    private boolean isTaken;
    private int dbId;

    public ShoppingListEntry(String name, double price, boolean isTaken, int dbId) {
        this.name = name;
        this.price = price;
        this.isTaken = isTaken;
        this.dbId = dbId;
    }

    public void update(String name, double price, boolean isTaken){
        this.name = name;
        this.price = price;
        this.isTaken = isTaken;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public int getDbId(){
        return this.dbId;
    }
}
