package com.example.lifeorganiser.src.Models.accounts;


import com.example.lifeorganiser.src.Models.user.User;

import java.math.BigDecimal;

public class DebitAccount extends Account {

    public DebitAccount(String accountName, double amount) {
        super(accountName, amount);
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
