package com.example.lifeorganiser.src.Models.accounts;


import com.example.lifeorganiser.src.Models.user.User;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebitAccount extends Account implements Serializable {

    public DebitAccount(String accountName, double amount, int dbUid) {
        super(accountName, amount, dbUid);
    }

    @Override
    public void withdrawMoney(double money) {
        super.amount -= money;
    }

    @Override
    public void insertMoney(double money) {
        super.amount += money;
    }

}
