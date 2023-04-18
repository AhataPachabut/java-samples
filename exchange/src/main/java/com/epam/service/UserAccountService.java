package com.epam.service;

import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.util.IOUtils;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UserAccountService {
    public UserAccount getUserAccount(User user) throws FileNotFoundException {
        return new UserAccount(user, IOUtils.readFromFile(user.name()));
    }

    public void updateUserAccount(UserAccount userAccount) throws IOException {
        IOUtils.writeToFile(userAccount.user().name(), userAccount.amountOfMoney());
    }
}
