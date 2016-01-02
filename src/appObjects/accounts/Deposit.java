package appObjects.accounts;


import appObjects.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class Deposit extends DebitAccount {

    double interest;
    Date maturity;

    public Deposit(String accountName, Currency currency, BigDecimal amount, double interest, Date maturity) {
        super(accountName, currency, amount);
        this.setInterest(interest);
        this.setMaturity(maturity);
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public Date getMaturity() {
        return maturity;
    }

    public void setMaturity(Date maturity) {
        this.maturity = maturity;
    }

    private BigDecimal expectedAmount(){
        throw new NotImplementedException();
    }

    public void generateTask(User user){
        throw new NotImplementedException();
    }
}
