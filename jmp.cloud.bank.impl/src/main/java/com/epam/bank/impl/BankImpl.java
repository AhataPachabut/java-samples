package com.epam.bank.impl;


import com.epam.bank.BankInterface;
import com.epam.entity.BankCard;
import com.epam.entity.BankCardType;
import com.epam.entity.CreditBankCard;
import com.epam.entity.DebitBankCard;
import com.epam.entity.User;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BankImpl implements BankInterface {

    private static Map<BankCardType, Function<User, BankCard>> handlers;

    static {
        handlers = new HashMap<>();
        handlers.put(BankCardType.CREDIT, CreditBankCard::new);
        handlers.put(BankCardType.DEBIT, DebitBankCard::new);
    }

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        if (handlers.containsKey(bankCardType)) {
            return handlers.get(bankCardType).apply(user);
        } else {
            throw new IllegalArgumentException("Invalid bank card type: " + bankCardType);
        }
    }
}
