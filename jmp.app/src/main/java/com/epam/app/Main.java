package com.epam.app;

import com.epam.bank.BankInterface;
import com.epam.entity.BankCardType;
import com.epam.entity.User;
import com.epam.exception.MissingBankCardException;
import com.epam.service.ServiceInterface;
import java.time.LocalDate;
import java.util.ServiceLoader;
import java.util.UUID;

public class Main {
    private final static BankInterface bankService = loadService(BankInterface.class);
    private final static ServiceInterface subscriptionService = loadService(ServiceInterface.class);

    public static void main(String[] args) {

        var user = new User(
            "John",
            "Doe",
            LocalDate.of(2000, 12, 31)
        );

        subscribe(user, BankCardType.CREDIT);
        subscribe(user, BankCardType.DEBIT);
        subscribe(user, null);
        subscribe(
            new User(
                "Ann",
                "Doe",
                LocalDate.of(2020, 5, 1)
            ),
            BankCardType.DEBIT
        );
        subscribe(
            new User(
                "Mark",
                "Doe",
                LocalDate.of(2023, 2, 1)
            ),
            BankCardType.DEBIT
        );

        var users = subscriptionService.getAllUsers();
        System.out.println("Users: " + users);

        var subscriptions = subscriptionService.getAllSubscriptionsByCondition(subscription ->
            subscription.bankCard().getUser().birthday().isAfter(LocalDate.of(2010, 1, 1))
        );
        System.out.println("Subscriptions of users born after 2010 year: " + subscriptions);

        subscriptions.forEach(subscription -> printSubscriptionByBankCardNumber(subscription.bankCard().getNumber()));
        printSubscriptionByBankCardNumber(UUID.randomUUID().toString());
    }

    private static <T> T loadService(Class<T> type) {
        return ServiceLoader.load(type).findFirst().orElseThrow(RuntimeException::new);
    }

    private static void subscribe(User user, BankCardType bankCardType) {
        try {
            subscriptionService.subscribe(
                bankService.createBankCard(
                    user,
                    bankCardType
                )
            );
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printSubscriptionByBankCardNumber(String bankCardNumber) {
        try {
            System.out.printf(
                "Subscription with bank card number %s: %s%n",
                bankCardNumber,
                subscriptionService.getSubscriptionByBankCardNumber(bankCardNumber)
                    .orElseThrow(() -> new MissingBankCardException(bankCardNumber))
            );
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}