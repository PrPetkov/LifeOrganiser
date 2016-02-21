package models.events;

import java.time.LocalDate;

import models.exceptions.IllegalAmountException;

public class PaymentEvent extends Event {

	private double amount;
	private boolean isIncome;
	private boolean isPaid;
	private boolean isOverdue;
	private LocalDate forDate;
	
	public PaymentEvent(String eventTitle,String description, double amount, boolean isIncome, boolean isPaid, LocalDate forDate) throws IllegalAmountException {
        super(eventTitle, description);
        this.isIncome = isIncome;
        this.isOverdue = checkIfOverdue();
        this.isPaid = isPaid;
        this.forDate = forDate;
        setAmount(amount);
    }


	// methods
    private boolean checkIfOverdue() {
		if (this.isPaid == false && this.forDate.isAfter(LocalDate.now())) {
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


	public LocalDate getForDate() {
		return forDate;
	}


	public void setForDate(LocalDate forDate) {
		this.forDate = forDate;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public void setIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}


	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}


	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}
    
}
