package appObjects.accounts;


import java.math.BigDecimal;
import java.util.Currency;

public class DebitCard extends DebitAccount {


    public DebitCard(String accountName, Currency currency, BigDecimal amount) {
        super(accountName, currency, amount);
    }
}
