package com.epam.service;

import com.epam.model.Currency;
import com.epam.model.ExchangeRate;
import com.epam.model.UserAccount;
import com.epam.util.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ExchangeService {
    private static final Collection<ExchangeRate> rates = new ArrayList<>();

    private UserAccountService userAccountService;

    public static void loadRates() throws IOException {
        rates.addAll(IOUtils.readFromFile("rates.txt", new TypeReference<ArrayList<ExchangeRate>>() {
        }));
    }

    public ExchangeService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void exchange(UserAccount userAccount, Currency from, Currency to, double amountOfMoney) throws Exception {
        var amountOfMoneyBefore = userAccountService.getSum(userAccount, from);
        if (amountOfMoneyBefore >= amountOfMoney) {
            var amountOfMoneyAfter = exchange(Currency.USD, Currency.EUR, amountOfMoney);
            userAccountService.setSum(userAccount, from, amountOfMoneyBefore - amountOfMoney);
            userAccountService.setSum(userAccount, to, userAccountService.getSum(userAccount, to) + amountOfMoneyAfter);
            System.out.println(userAccount);
        } else {
            throw new Exception("Unable to exchange because not enough money");
        }
    }

    private double exchange(Currency from, Currency to, double amountOfMoney) throws Exception {
        var rate = rates.stream()
            .filter(exchangeRate -> exchangeRate.currencyFrom() == from && exchangeRate.currencyTo() == to)
            .findFirst()
            .orElseThrow(() -> new Exception("Unable to exchange because rates are unknown "))
            .rate();
        return amountOfMoney * rate;
    }


}
