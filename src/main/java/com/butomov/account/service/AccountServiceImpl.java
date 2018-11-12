package com.butomov.account.service;

import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.AccountNotFoundException;
import com.butomov.account.exceptions.UserNotFoundException;
import com.butomov.account.model.Account;
import com.butomov.account.model.User;
import com.butomov.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Override
    public Account createAccount(UUID userId)
            throws AccountExistsException, UserNotFoundException {
        log.info("creating account...");

        final User user = Optional.ofNullable(userService.getUser(userId))
                .orElseThrow(UserNotFoundException::new);

        if (nonNull(getAccount(userId))) {
            log.error("account exists already...");
            throw new AccountExistsException();
        }

        Account account = Account.builder()
                .user(user)
                .amount(0d)
                .build();
        updateOrCreateAccount(account);
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
    public Double getAmount(long accountId) throws AccountNotFoundException {
        Account account = Optional.ofNullable(getAccount(accountId))
                .orElseThrow(AccountNotFoundException::new);
        return account.getAmount();
    }

    @Override
    public void updateOrCreateAccount(Account account) {
        accountRepository.save(account);
    }
}
