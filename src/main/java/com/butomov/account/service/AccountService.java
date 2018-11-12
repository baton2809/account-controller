package com.butomov.account.service;

import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.AccountNotFoundException;
import com.butomov.account.exceptions.UserNotFoundException;
import com.butomov.account.model.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AccountService {

    Account createAccount(UUID userId) throws AccountExistsException, UserNotFoundException;

    Account getAccount(UUID userId);

    Account getAccount(long accountId);

    Double getAmount(long accountId) throws AccountNotFoundException;

    void updateOrCreateAccount(Account account);
}
