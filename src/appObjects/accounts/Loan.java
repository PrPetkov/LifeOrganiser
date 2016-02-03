package appObjects.accounts;


import appObjects.User;
import appObjects.tasks.PayTask;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Date;

public class Loan extends CreditAccount {

    private LocalDateTime maturity;

    public Loan(String accountName, Currency currency, BigDecimal amount, double interest, LocalDateTime maturity, LocalDateTime payDay) {
        super(accountName, currency, amount, interest, maturity);
        this.setMaturity(maturity);
    }

    public LocalDateTime getMaturity() {
        return maturity;
    }

    public void setMaturity(LocalDateTime maturity) {
        this.maturity = maturity;
    }

    public void generateTask(User user){
        user.addTask(new PayTask("Credit pay day", super.getPayDay(), super.getAmount()));
    }
}
