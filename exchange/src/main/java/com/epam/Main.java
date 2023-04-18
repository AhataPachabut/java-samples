package com.epam;

import com.epam.model.Currency;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.service.ExchangeService;
import com.epam.service.UserAccountService;

public class Main {
    public static void main(String[] args) throws Exception {
        ExchangeService.loadRates();

        var exchangeService = new ExchangeService();
        var userAccountService = new UserAccountService();

        User user1 = new User("Mark");
        UserAccount account1 = userAccountService.getUserAccount(user1);

        var value = exchangeService.exchange(Currency.USD, Currency.EUR, account1.amountOfMoney().get(Currency.USD));
        account1.amountOfMoney().put(Currency.EUR, value);
        userAccountService.updateUserAccount(account1);
    }
}