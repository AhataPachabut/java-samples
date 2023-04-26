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

    public void loadRates() throws IOException {
        rates.addAll(IOUtils.readFromFile("rates.txt", new TypeReference<ArrayList<ExchangeRate>>() {
        }));
    }

    public ExchangeService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    //synchronized because it is atomic operation. moreover other threads should not update userAccount at the same time.
    public void exchange(UserAccount userAccount, Currency from, Currency to, double amountOfMoneyFrom) throws Exception {
        synchronized (userAccount) {
            var amountOfMoneyFromBefore = userAccountService.getSum(userAccount, from);
            if (amountOfMoneyFromBefore >= amountOfMoneyFrom) {
                var amountOfMoneyToAfter = exchange(Currency.USD, Currency.EUR, amountOfMoneyFrom);
                userAccountService.setSum(userAccount, from, amountOfMoneyFromBefore - amountOfMoneyFrom);
                userAccountService.setSum(userAccount, to, userAccountService.getSum(userAccount, to) + amountOfMoneyToAfter);
            } else {
                throw new Exception("Unable to exchange because not enough money");
            }
            Thread.sleep(5000);//this is to see that one thread executes after another
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
