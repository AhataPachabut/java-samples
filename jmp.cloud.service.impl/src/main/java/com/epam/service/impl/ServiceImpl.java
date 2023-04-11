package com.epam.service.impl;

import com.epam.entity.BankCard;
import com.epam.entity.Subscription;
import com.epam.entity.User;
import com.epam.service.ServiceInterface;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceImpl implements ServiceInterface {

    private final Collection<Subscription> subscriptions = new ArrayList<>();

    @Override
    public void subscribe(BankCard bankCard) {
        subscriptions.add(new Subscription(bankCard, LocalDate.now()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) {
        return subscriptions.stream()
            .filter(subscription -> subscription.bankCard().getNumber().equals(bankCardNumber))
            .findFirst();
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptions.stream()
            .filter(condition)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<User> getAllUsers() {
        return subscriptions.stream()
            .map(subscription -> subscription.bankCard().getUser())
            .distinct()
            .collect(Collectors.toUnmodifiableList());
    }
}
