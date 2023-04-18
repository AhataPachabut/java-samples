package com.epam.service;

import com.epam.model.Currency;
import com.epam.model.ExchangeRate;
import com.epam.util.IOUtils;
import java.io.FileNotFoundException;
import java.util.Collection;

public class ExchangeService {
    private static Collection<ExchangeRate> rates;

    public static void loadRates() throws FileNotFoundException {
        rates = IOUtils.readFromFile("rates");
    }

    public double exchange(Currency from, Currency to, double amountOfMoney) throws Exception {
        var rate = rates.stream()
            .filter(exchangeRate -> exchangeRate.currencyFrom() == from && exchangeRate.currencyTo() == to)
            .findFirst()
            .orElseThrow(() -> new Exception("Unable to exchange currencies %s->%s".formatted(from, to)))
            .rate();
        return amountOfMoney * rate;
    }
}
