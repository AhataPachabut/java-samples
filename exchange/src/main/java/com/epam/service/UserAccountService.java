package com.epam.service;

import com.epam.model.Currency;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.util.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class UserAccountService {

    private static final Collection<UserAccount> userAccounts = new HashSet<>();

    public void saveUserAccounts() {
        userAccounts.parallelStream().forEach(userUserAccountEntry -> {
            try {
                IOUtils.writeToFile(
                    userUserAccountEntry.user().name() + ".txt",
                    userUserAccountEntry.amountOfMoney());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //synchronized because threads should not re-initialize userAccount from file
    public synchronized UserAccount getUserAccount(User user) {
        return userAccounts.stream().filter(userAccount -> userAccount.user() == user).findFirst().or(() -> {
            UserAccount userAccount;
            try {
                userAccount = new UserAccount(
                    user,
                    IOUtils.readFromFile(
                        user.name() + ".txt",
                        new TypeReference<HashMap<Currency, Double>>() {
                        }
                    )
                );
            } catch (FileNotFoundException e) {
                userAccount = new UserAccount(
                    user,
                    new HashMap<>()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userAccounts.add(userAccount);
            return Optional.of(userAccount);
        }).get();
    }

    public Double getSum(UserAccount userAccount, Currency currency) {
        return userAccount.amountOfMoney().getOrDefault(currency, 0.0);
    }

    //other threads should not update userAccount at the same time.
    public void setSum(UserAccount userAccount, Currency currency, Double sum) {
        synchronized (userAccount) {
            userAccounts.add(userAccount);
            userAccount.amountOfMoney().put(currency, sum);
            System.out.println(Thread.currentThread().getId() + " " + userAccount);
        }
    }
}
