package Models.accounts;


import Exceptions.AccountException;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class DebitAccount extends Account {


	public DebitAccount(String accountName, Currency currency, BigDecimal amount) {
		super(accountName, currency, amount);
	}

    @Override
    public void withdrawMoney(BigDecimal money) {
        try{
        super.setAmount(super.getAmount().subtract(money));
        } catch (AccountException e){
            //TODO throw exception to the user
        }
    }

    @Override
    public void insertMoney(BigDecimal money) {
        try{
        super.setAmount(super.getAmount().add(money));
        } catch (AccountException e){
            //TODO throw exception to the user
        }
    }
}
