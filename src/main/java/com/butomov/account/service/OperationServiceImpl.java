package com.butomov.account.service;

import com.butomov.account.domain.Account;
import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.LimitMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class OperationServiceImpl implements OperationService {

    public static final double MAX_TRANSFER_LIMIT = 1000000.0;
    public static final double MIN_TRANSFER_LIMIT = 1.0;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OperationService operationService;

    @Override
    public boolean transfer(long senderId, long payeeId, double amount)
            throws LimitMoneyException, AccountExistsException {

        if (amount < MIN_TRANSFER_LIMIT || amount > MAX_TRANSFER_LIMIT) {
            throw new LimitMoneyException();
        }

        withdraw(senderId, amount);
        refill(payeeId, amount);
        return true;
    }

    @Override
    public double refill(long accountId, Double amount)
            throws LimitMoneyException, AccountExistsException {

        if (amount < MIN_TRANSFER_LIMIT || amount > MAX_TRANSFER_LIMIT) {
            throw new LimitMoneyException();
        }

        Account account = accountService.getAccount(accountId);
        if (isNull(account)) {
            throw new AccountExistsException();
        }

        account.setAmount(account.getAmount() + amount);
        accountService.updateAccount(account);
        return account.getAmount();
    }

    @Override
    public boolean withdraw(long accountId, double amount)
            throws LimitMoneyException, AccountExistsException {

        if (amount < MIN_TRANSFER_LIMIT || amount > MAX_TRANSFER_LIMIT) {
            throw new LimitMoneyException();
        }

        Account account = accountService.getAccount(accountId);
        if (isNull(account)) {
            throw new AccountExistsException();
        }

        if (amount > account.getAmount()) {
            throw new LimitMoneyException();
        }

        account.setAmount(account.getAmount() - amount);
        accountService.updateAccount(account);
        return true;
    }
}
