package com.example.lifeorganiser.src.Models.events;

import com.example.lifeorganiser.src.Models.Exceptions.IllegalAmountException;

import java.util.Calendar;

public class PaymentEvent extends DatedEvent {

	private double amount;
	private boolean isIncome;
	private boolean isPaid;
	private boolean isOverdue;
	
	public PaymentEvent(String eventTitle,String description, double amount, boolean isIncome, boolean isPaid, Calendar dateTime) throws IllegalAmountException {
        super(eventTitle, description, dateTime);
        this.isIncome = isIncome;
        this.isOverdue = checkIfOverdue();
        this.isPaid = isPaid;
        
        setAmount(amount);
    }


	// methods
    private boolean checkIfOverdue() {
		if (!this.isPaid && super.getDateTime().before(Calendar.getInstance())) {
			return true;
		}
		return false;
	}

    private void setAmount(Double amount) throws IllegalAmountException {
        if (amount != null && amount >= 0) {
        	this.amount = amount;
        } else {
        	throw new IllegalAmountException("The entered amount for your payment must be positive!");	
        }
    }
    
    // getters and setters
	public double getAmount() {
        return this.amount;
    }
    
    public boolean getIsIncome(){
    	return this.isIncome;
    }

    public boolean getIsOverdue(){
    	return this.isOverdue;
    }
    
    public boolean getIsPaid(){
    	return this.isPaid;
    }

}
