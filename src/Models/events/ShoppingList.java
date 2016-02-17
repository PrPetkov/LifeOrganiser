package Models.tasks;

import java.util.ArrayList;
import java.util.Map;

public class ShoppingList{

	private double isPaid;
	private double amount;
	//I'm not sure that Entry of ArrayList will work.	
    private ArrayList<Entry<String,Double>> entries;

    public ShoppingList(){
    	this.amount = 0;
    	this.isPaid = false;
    	this.entries = new ArrayList<Entry<String,Double>>();
    }

    public void addEntry(String name,Double amount){
       try{
    	if (this.setEntryAmount(amount)){
    		this.entries.add(new Entry(name,amount));
        }
       }catch(/*validateExeption e*/){
    	   //e.getMessage();
       }
   
    }

    public void removeEntry(String name){
        if (this.entries.contains(name)){
        	this.entries.remove(name);
        }
    }
    
    private boolean setEntryAmount(double amount){
    	if(amount < 0){
    		//throw ValidateExeption
    	}
    	return true;
    }

    public Iterable<IShoppingEntry> getShoppingListEnties(){
        return this.entries;
    }
    
    //calculate amount for friendly message to user
    public double getAmountOfAllEntries(){
    	//for amount += getValue();
    	return amount;
    }
    
    public double setAmountOfShoppingList(double amount){
    	if(this.amount > 0)
    		this.amount = amount;
    	else
    		//trow ValidateExeption
    }
    
    public boolean getIsPaid(){
    	return this.isPaid;
    }

}
