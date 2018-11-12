package com.butomov.account.service;

import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.LimitMoneyException;
import com.butomov.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class OperationServiceImpl implements OperationService {

    public static final double MAX_TRANSFER_LIMIT = 1000000d;
    public static final double MIN_TRANSFER_LIMIT = 1d;

    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public boolean transfer(long senderId, long payeeId, double amount)
            throws LimitMoneyException, AccountExistsException {
        log.debug("transfer calls...");

        if (amount < MIN_TRANSFER_LIMIT || amount > MAX_TRANSFER_LIMIT) {
            log.error("amount doesn't fit limit...");
            throw new LimitMoneyException();
        }

        withdraw(senderId, amount);
        refill(payeeId, amount);
        log.debug("transfer transaction SUCCESS.");
        return true;
    }

    @Override
    @Transactional
    public double refill(long accountId, double amount)
            throws LimitMoneyException, AccountExistsException {
        log.debug("refill calls...");

        if (amount < MIN_TRANSFER_LIMIT || amount > MAX_TRANSFER_LIMIT) {
            log.error("amount doesn't fit limit...");
            throw new LimitMoneyException();
        }

        Account account = Optional.ofNullable(accountService.getAccount(accountId))
                .orElseThrow(AccountExistsException::new);

        account.setAmount(account.getAmount() + amount);
        log.debug("updates amount...");
        accountService.updateOrCreateAccount(account);
        log.debug("refill transaction SUCCESS.");
        return account.getAmount();
    }

    @Override
    @Transactional
    public double withdraw(long accountId, double amount)
            throws LimitMoneyException, AccountExistsException {
        log.debug("withdraw calls...");

        if (amount < MIN_TRANSFER_LIMIT || amount > MAX_TRANSFER_LIMIT) {
            log.error("amount doesn't fit limit...");
            throw new LimitMoneyException();
        }

        final Account account = Optional.ofNullable(accountService.getAccount(accountId))
                .orElseThrow(AccountExistsException::new);

        if (amount > account.getAmount()) {
            log.error("amount is incorrect...");
            throw new LimitMoneyException();
        }

        account.setAmount(account.getAmount() - amount);
        log.debug("updates amount...");
        accountService.updateOrCreateAccount(account);
        log.debug("withdraw transaction SUCCESS.");
        return account.getAmount();
    }
}
