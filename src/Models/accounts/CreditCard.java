package Models.accounts;


import Exceptions.AccountException;
import Models.User;
import Models.tasks.PayTask;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class CreditCard extends CreditAccount {

    private BigDecimal limit;

    public CreditCard(String accountName, Currency currency, BigDecimal amount, double interest, LocalDateTime payDay, BigDecimal limit) {
        super(accountName, currency, amount, interest, payDay);

    }

    @Override
    protected void setAmount(BigDecimal amount) throws AccountException {
        if ((new BigDecimal(0).add(super.getAmount().add(amount)).compareTo(this.limit)) <= 0 ) {
            super.setAmount(amount.add(super.getAmount()));
        }else {
            throw new AccountException("The credit card can not exceed it's limit");
        }
    }


    @Override
    public void generateTask(User user) {
        user.addTask(new PayTask("Credit card payment", super.getPayDay(), super.getAmount()));
    }
}
