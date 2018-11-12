package com.butomov.account.service;


import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.LimitMoneyException;
import org.springframework.stereotype.Service;

@Service
public interface OperationService {

    boolean transfer(long senderId, long payeeId, double amount) throws LimitMoneyException, AccountExistsException;

    double refill(long accountId, double amount) throws LimitMoneyException, AccountExistsException;

    double withdraw(long accountId, double amount) throws LimitMoneyException, AccountExistsException;
}
