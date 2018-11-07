package com.butomov.account.service;

import com.butomov.account.domain.Account;
import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AccountService {

    /**
     * Creates an Account object
     *
     * @return
     */
    Account createAccount(UUID userId) throws AccountExistsException, UserNotFoundException;

    Account getAccount(UUID userId);

    Account getAccount(long accountId);

    Double getAmount(long accountId);

    void updateAccount(Account account);
}
