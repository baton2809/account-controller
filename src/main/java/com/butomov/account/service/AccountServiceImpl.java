package com.butomov.account.service;

import com.butomov.account.domain.Account;
import com.butomov.account.domain.User;
import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.UserNotFoundException;
import com.butomov.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Override
    public Account createAccount(UUID userId)
            throws AccountExistsException, UserNotFoundException {

        if (nonNull(getAccount(userId))) {
            throw new AccountExistsException();
        }

        final User user = userService.getUser(userId);
        if (isNull(user)) {
            throw new UserNotFoundException();
        }

        Account account = new Account(user);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account getAccount(UUID userId) {
        return accountRepository.findByUserUserId(userId);
    }

    @Override
    public Account getAccount(long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public Double getAmount(long accountId) {
        return getAccount(accountId).getAmount();
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}
