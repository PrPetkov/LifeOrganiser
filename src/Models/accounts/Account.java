package models.accounts;


import models.exceptions.AccountException;
import models.User;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class Account {
	
	private String accountName;
	private Currency currency;
	private BigDecimal amount;

	public Account(String accountName, Currency currency, BigDecimal amount) {

		this.setAccountName(accountName);
		this.setCurrency(currency);
        try{
		this.setAmount(amount);
        } catch (AccountException e){
            //TODO throw exception to the user
        }

	}

	public Currency getCurrency() {
		return currency;
	}

	protected void setCurrency(Currency currency) {
		if (currency == null){
            return;
        }

		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	protected void setAmount(BigDecimal amount) throws AccountException {
		if (amount.compareTo(new BigDecimal(0)) >= 0) {
            this.amount = amount;
        }
	}
	
	public void setAccountName(String accountName) {
        if (accountName != null) {
            this.accountName = accountName;
        }
	}
	
	public String getAccountName() {
		return this.accountName;
	}

    public abstract void withdrawMoney(BigDecimal money);

    public abstract void insertMoney(BigDecimal money);

    public abstract void generateTask(User user);

}