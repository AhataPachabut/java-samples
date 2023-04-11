package com.epam.service;

import com.epam.entity.BankCard;
import com.epam.entity.Subscription;
import com.epam.entity.User;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ServiceInterface {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber);

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        var currentDate = LocalDate.now();
        return getAllUsers()
            .stream()
            .mapToLong(user -> getUserAge(user, currentDate))
            .average()
            .getAsDouble();
    }

    static boolean isPayableUser(User user) {
        return getUserAge(user, LocalDate.now()) >= 18;
    }

    private static int getUserAge(User user, LocalDate currentDate) {
        return Period.between(user.birthday(), currentDate).getYears();
    }
}
