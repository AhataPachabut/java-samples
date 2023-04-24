package com.epam.model;

import java.util.Map;

public record UserAccount(
    User user,
    Map<Currency, Double> amountOfMoney //Only 1 thread at a time should work with this. but I don't want to manage it here.
) {
}
