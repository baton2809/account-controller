package com.butomov.account.service;


import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.LimitMoneyException;
import org.springframework.stereotype.Service;

@Service
public interface OperationService {

    /**
     * Transfer money from sender Account to payee Account
     *
     * @param senderId
     * @param payeeId
     * @param amount
     * @return status of operation
     */
    boolean transfer(long senderId, long payeeId, double amount) throws LimitMoneyException, AccountExistsException;

    /**
     * Refill money to Account
     *
     * @param accountId
     * @param amount
     * @return status of operation
     */
    double refill(long accountId, Double amount) throws LimitMoneyException, AccountExistsException;

    /**
     * @param accountId
     * @param amount
     * @return status of operation
     */
    boolean withdraw(long accountId, double amount) throws LimitMoneyException, AccountExistsException;
}
