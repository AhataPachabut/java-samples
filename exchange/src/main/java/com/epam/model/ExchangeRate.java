package com.epam.model;

public record ExchangeRate(
    Currency currencyFrom,
    Currency currencyTo,
    double rate
) {
}
