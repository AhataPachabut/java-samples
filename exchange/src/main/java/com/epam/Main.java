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
    private static ExchangeService exchangeService = new ExchangeService(userAccountService);

    public static void main(String[] args) throws Exception {
        ExchangeService.loadRates();

        Executor executor = Executors.newFixedThreadPool(10);
        Runnable task = () -> {
            User user = new User("Mark");
            try {
                UserAccount userAccount = userAccountService.getUserAccount(user);
                exchangeService.exchange(userAccount, Currency.USD, Currency.EUR, 50.0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Runnable task1 = () -> {
            User user = new User("Mark");
            try {
                UserAccount userAccount = userAccountService.getUserAccount(user);
                userAccountService.plusSum(userAccount, Currency.USD, 1000.0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        for (var i = 0; i < 2; i++) {
            executor.execute(task);
//            executor.execute(task1);
        }

        Thread.currentThread().join();
        System.exit(1);
//        UserAccountService.saveUserAccounts();
    }


}