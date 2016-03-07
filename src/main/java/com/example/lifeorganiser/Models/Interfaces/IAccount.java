package com.example.lifeorganiser.src.Models.Interfaces;

import com.example.lifeorganiser.src.Models.user.User;

import java.math.BigDecimal;
import java.util.Currency;

public interface IAccount {

    void withdrawMoney(BigDecimal money);

    void insertMoney(BigDecimal money);

    void generateTask(User user);

    Currency getCurrency();

    BigDecimal getAmount();

    String getAccountName();
}
