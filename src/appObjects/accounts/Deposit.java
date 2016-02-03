package appObjects.accounts;


import appObjects.User;
import appObjects.tasks.RecieveMoneyTask;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class Deposit extends DebitAccount {

    double interest;
    LocalDateTime maturity;

    public Deposit(String accountName, Currency currency, BigDecimal amount, double interest, LocalDateTime maturity) {
        super(accountName, currency, amount);
        this.setInterest(interest);
        this.setMaturity(maturity);
    }

    public double getInterest() {
        return interest;
    }

    private void setInterest(double interest) {
        if (interest >= 0) {
            this.interest = interest;
        }
    }

    public LocalDateTime getMaturity() {
        return maturity;
    }

    private void setMaturity(LocalDateTime maturity) {
        this.maturity = maturity;
    }

    private BigDecimal expectedAmount(){
        throw new NotImplementedException();
    }

    public void generateTask(User user){
        user.addTask(new RecieveMoneyTask("Deposit maturiry", this.maturity, super.getAmount()));
    }
}
