package com.epam.service;

import com.epam.model.Currency;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.util.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserAccountService {

    private static final Map<User, UserAccount> map = new HashMap<>();

    public static void saveUserAccounts() {
        map.entrySet().parallelStream().forEach(userUserAccountEntry -> {
            try {
                IOUtils.writeToFile(
                    userUserAccountEntry.getKey().name() + ".txt",
                    userUserAccountEntry.getValue().amountOfMoney());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public UserAccount getUserAccount(User user) throws IOException {
        if (map.containsKey(user)) {
            return map.get(user);
        } else {
            UserAccount userAccount;
            try {
                userAccount= new UserAccount(user,
                    IOUtils.readFromFile(
                        user.name() + ".txt",
                        new TypeReference<HashMap<Currency, Double>>() {
                        }
                    )
                );
            } catch (FileNotFoundException e) {
                userAccount= new UserAccount(user, new HashMap<>());
            }
            map.put(user,userAccount);
            return userAccount;
        }
    }

    public Double getSum(UserAccount userAccount, Currency currency) {
        return userAccount.amountOfMoney().getOrDefault(currency, 0.0);
    }

    public void setSum(UserAccount userAccount, Currency currency, Double sum) {
        map.put(userAccount.user(), userAccount);
        userAccount.amountOfMoney().put(currency, sum);
    }
}
