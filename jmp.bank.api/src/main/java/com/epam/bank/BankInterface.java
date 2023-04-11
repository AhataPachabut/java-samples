package com.epam.bank;

import com.epam.entity.BankCard;
import com.epam.entity.BankCardType;
import com.epam.entity.User;

public interface BankInterface {
    BankCard createBankCard(User user, BankCardType bankCardType) throws IllegalArgumentException;
}
