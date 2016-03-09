package com.example.lifeorganiser.src.Models.accounts;

import com.example.lifeorganiser.src.Models.Exceptions.AccountException;
import com.example.lifeorganiser.src.Models.events.PaymentEvent;
import com.example.lifeorganiser.src.Models.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public abstract class Account {
	
	private String accountName;
	protected double amount;
	private ArrayList<PaymentEvent> paymentHistory;
    private int dbUid;

	public Account(String accountName, double amount, int dbUid) {
        this.paymentHistory = new ArrayList<>();
		this.setAccountName(accountName);
		this.amount = amount;
        this.dbUid = dbUid;
	}

    public ArrayList<PaymentEvent> getPaymentHistory() {
        return paymentHistory;
    }

    public void addPaymentEvent(PaymentEvent payment) {
        this.paymentHistory.add(payment);
    }

    public double getAmount() {
		return amount;
	}
	
	public void setAccountName(String accountName) {
        if (accountName != null) {
            this.accountName = accountName;
        }
	}

    public int getDbUid() {
        return dbUid;
    }

    public String getAccountName() {
		return this.accountName;
	}

    public abstract void withdrawMoney(double money);

    public abstract void insertMoney(double money);
}