package models.events;

import models.exceptions.IllegalAmountException;

public class PaymentEvent extends Event {

	//LocalDate forDate - for witch date is the current event
	private double amount;
	private boolean isIncome;
	private boolean isPaid;
	private boolean isOverdue;
	
	public PaymentEvent(String eventTitle,String description, double amount, boolean isIncome, boolean isPaid) throws IllegalAmountException {
        super(eventTitle, description);
        this.isIncome = isIncome;
        this.isOverdue = checkIfOverdue();
        this.isPaid = isPaid;
        // this.forDate = SOME PICKED DATE FROM THE USER
        
        setAmount(amount);
    }


	// methods
    private boolean checkIfOverdue() {
		if (this.isPaid == false /* && CURRENT DATE > forDate */) {
			
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
