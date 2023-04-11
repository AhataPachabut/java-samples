package com.epam.entity;

import java.util.UUID;

public abstract class BankCard {

    private final String number = UUID.randomUUID().toString();

    private final User user;

    protected BankCard(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public User getUser() {
        return user;
    }
}
