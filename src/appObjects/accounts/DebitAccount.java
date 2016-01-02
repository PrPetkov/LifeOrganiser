package appObjects.accounts;


import java.math.BigDecimal;
import java.util.Currency;

public abstract class DebitAccount extends Account {


	public DebitAccount(String accountName, Currency currency, BigDecimal amount) {
		super(accountName, currency, amount);
	}
}
