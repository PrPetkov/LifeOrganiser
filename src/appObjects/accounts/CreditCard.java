package appObjects.accounts;


import appObjects.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class CreditCard extends CreditAccount {

    int gratisPeriodDays;

    public CreditCard(String accountName, Currency currency, BigDecimal amount, double interest, Date payDay, int gratisPeriodDays) {
        super(accountName, currency, amount, interest, payDay);
        this.setGratisPeriodDays(gratisPeriodDays);
    }

    public int getGratisPeriodDays() {
        return gratisPeriodDays;
    }

    public void setGratisPeriodDays(int gratisPeriodDays) {
        this.gratisPeriodDays = gratisPeriodDays;
    }

    @Override
    protected void generateTask(User user) {
        throw new NotImplementedException();
    }
}
