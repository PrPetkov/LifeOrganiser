package appObjects.accounts;


import appObjects.User;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public abstract class CreditAccount extends Account {
	
	private double interest;
	private Date payDay;

	public CreditAccount(String accountName, Currency currency, BigDecimal amount, double interest, Date payDay) {
		super(accountName, currency, amount);
		this.setInterest(interest);
		this.setPayDay(payDay);
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public Date getPayDay() {
		return payDay;
	}

	public void setPayDay(Date payDay) {
		this.payDay = payDay;
	}

	protected abstract void generateTask(User user);
}
