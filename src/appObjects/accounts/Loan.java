package appObjects.accounts;


import appObjects.User;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class Loan extends CreditAccount {

    private Date maturity;

    public Loan(String accountName, Currency currency, BigDecimal amount, double interest, Date payDay, Date maturity) {
        super(accountName, currency, amount, interest, payDay);
        this.setMaturity(maturity);
    }

    public Date getMaturity() {
        return maturity;
    }

    public void setMaturity(Date maturity) {
        this.maturity = maturity;
    }

    @Override
    protected void generateTask(User user) {

    }
}
