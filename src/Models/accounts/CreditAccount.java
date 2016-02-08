package Models.accounts;


import Exceptions.AccountException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public abstract class CreditAccount extends Account {
	
	private double interest;
	private LocalDateTime payDay;

	public CreditAccount(String accountName, Currency currency, BigDecimal amount, double interest, LocalDateTime payDay) {
		super(accountName, currency, amount);
		this.setInterest(interest);
		this.setPayDay(payDay);
	}

	public double getInterest() {
		return interest;
	}

	private void setInterest(double interest) {
		this.interest = interest;
	}

	public LocalDateTime getPayDay() {
		return payDay;
	}

	private void setPayDay(LocalDateTime payDay) {
		this.payDay = payDay;
	}

    @Override
    protected void setAmount(BigDecimal amount) throws AccountException {
        super.setAmount(amount);
    }

    @Override
    public void withdrawMoney(BigDecimal money) {
        try {
            super.setAmount(super.getAmount().add(money));
        } catch (AccountException e){
            //TODO throw exception to the user
        }

    }

    @Override
    public void insertMoney(BigDecimal money) {
        try{
        super.setAmount(super.getAmount().subtract(money));
        } catch (AccountException e){
            //TODO throw exception to the user
        }
    }
}
