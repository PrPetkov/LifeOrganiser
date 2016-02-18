package Models.tasks;

import java.util.ArrayList;
import java.util.Map;

public class ShoppingList{

	private double isPaid;
	private double amount;
    private HashMap<String,Double> entries;

    public ShoppingList(){
    	this.amount = 0;
    	this.isPaid = false;
    	this.entries = new HashMap<String,Double>();
    }

    public void addEntry(String name,Double amount){
       try{
    	if (this.setEntryAmount(amount)){
    		this.entries.add(name,amount);
        }
       }catch(IllegalAmounExeption e){
    	   	e.getMessage();
       }
   
    }

    public void removeEntry(String name){
        if (this.entries.contains(name)){
        	this.entries.remove(name);
        }
    }
    
    private boolean setEntryAmount(double amount){
    	if(amount < 0){
    		throw new IllegalAmountExeption("Invalid value of entry !");
    	}
    	return true;
    }

    public Iterable<IShoppingEntry> getShoppingListEnties(){
        return this.entries;
    }
    
    public double getAmountOfAllEntries(){
    	double currentAmountOfList;
    	for(Map.Entry<String, Integer> map : this.entries){
    		int value = map.getValue();
    		currentAmountOfList += value;
    	}
    	return currentAmountOfList;
    }
    
    public double setAmountOfShoppingList(double amount){
    	if(this.amount > 0)
    		this.amount = amount;
    	else
    		throw new IllegalAmountexeption("The amount must be positive !");
    }
    
    public boolean getIsPaid(){
    	return this.isPaid;
    }

}
