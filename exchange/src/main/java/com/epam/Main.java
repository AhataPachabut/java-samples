package com.epam;

import com.epam.model.Currency;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.service.ExchangeService;
import com.epam.service.UserAccountService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {

    private static UserAccountService userAccountService = new UserAccountService();
    private static final ExchangeService exchangeService = new ExchangeService(userAccountService);

    public static void main(String[] args) throws Exception {
        exchangeService.loadRates();

        User user1 = new User("Mark");
        User user2 = new User("Ann");

        Executor executor = Executors.newFixedThreadPool(10);
        Runnable task1 = () -> {
            try {
                UserAccount userAccount = userAccountService.getUserAccount(user1);
                exchangeService.exchange(userAccount, Currency.USD, Currency.EUR, 50.0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        Runnable task2 = () -> {
            try {
                UserAccount userAccount = userAccountService.getUserAccount(user2);
                exchangeService.exchange(userAccount, Currency.USD, Currency.EUR, 50.0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        for (var i = 0; i < 2; i++) {
            executor.execute(task1);
            executor.execute(task2);
            executor.execute(() -> {
                try {
                    UserAccount userAccount = userAccountService.getUserAccount(user1);
                    userAccountService.setSum(userAccount, Currency.EUR, 1000.0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            executor.execute(() -> {
                try {
                    UserAccount userAccount = userAccountService.getUserAccount(user1);
                    userAccountService.setSum(userAccount, Currency.USD, 1000.0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

//        userAccountService.saveUserAccounts();
    }


}