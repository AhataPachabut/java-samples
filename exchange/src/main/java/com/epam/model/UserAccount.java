package com.epam.model;

import java.util.Map;

public record UserAccount(
    User user,
    Map<Currency, Double> amountOfMoney
) {
}
