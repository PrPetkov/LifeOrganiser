package appObjects.accounts;


import Interfaces.IAccount;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class Account implements IAccount {
	
	private String accountName;
	private Currency currency;
	private BigDecimal amount;

	public Account(String accountName, Currency currency, BigDecimal amount) {

		this.setAccountName(accountName);
		this.setCurrency(currency);
		this.setAmount(amount);

	}

	public Currency getCurrency() {

		return currency;
	}

	private void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountName() {
		return this.accountName;
	}
	

}
