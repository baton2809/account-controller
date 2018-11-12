package com.butomov.account.service;

import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.LimitMoneyException;
import com.butomov.account.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.when;

public class OperationServiceImplTest {

    @InjectMocks
    private OperationServiceImpl operationService;

    @Mock
    private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldRefillCorrectly() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = ThreadLocalRandom.current().nextDouble(1000d, 10000d);
        double toRefill = ThreadLocalRandom.current().nextDouble(1d, 1000d);
        final Account account = Account.builder().amount(accountAmount).build();
        when(accountService.getAccount(accountId)).thenReturn(account);

        double result = operationService.refill(accountId, toRefill);

        Assert.assertEquals(result, accountAmount + toRefill, 0.0001);
    }

    @Test(expected = AccountExistsException.class)
    public void shouldThrowExceptionAccountExistsWhenRefill() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = ThreadLocalRandom.current().nextDouble(1000d, 10000d);
        when(accountService.getAccount(accountId)).thenReturn(null);

        operationService.refill(accountId, accountAmount);
    }

    @Test
    public void shouldWithdrawCorrectly() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = ThreadLocalRandom.current().nextDouble(1000d, 10000d);
        double toWithdraw = ThreadLocalRandom.current().nextDouble(1d, 1000d);
        final Account account = Account.builder().amount(accountAmount).build();
        when(accountService.getAccount(accountId)).thenReturn(account);

        double result = operationService.withdraw(accountId, toWithdraw);

        Assert.assertEquals(result, accountAmount - toWithdraw, 0.0001);
    }

    @Test(expected = AccountExistsException.class)
    public void shouldThrowExceptionAccountExistsWhenWithdraw() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = ThreadLocalRandom.current().nextDouble(1000d, 10000d);
        when(accountService.getAccount(accountId)).thenReturn(null);

        operationService.withdraw(accountId, accountAmount);
    }


    @Test
    public void shouldTransferCorrectly() throws Exception {
        long accountId1 = ThreadLocalRandom.current().nextLong();
        long accountId2 = ThreadLocalRandom.current().nextLong();
        double accountAmount1 = ThreadLocalRandom.current().nextDouble(1000d, 10000d);
        double accountAmount2 = accountAmount1;
        double toTransfer = ThreadLocalRandom.current().nextDouble(1d, 1000d);
        final Account account1 = Account.builder().amount(accountAmount1).build();
        final Account account2 = Account.builder().amount(accountAmount2).build();
        when(accountService.getAccount(accountId1)).thenReturn(account1);
        when(accountService.getAccount(accountId2)).thenReturn(account2);

        operationService.transfer(accountId1, accountId2, toTransfer);

        Assert.assertNotEquals(account1.getAmount(), account2.getAmount());
        Assert.assertEquals(2 * toTransfer, account2.getAmount() - account1.getAmount(), 0.0001);
    }

    @Test(expected = LimitMoneyException.class)
    public void shouldThrowExceptionLimitLowMoneyWhenWithdraw() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = 0;

        operationService.withdraw(accountId, accountAmount);
    }

    @Test(expected = LimitMoneyException.class)
    public void shouldThrowExceptionLimitLimitLowMoneyWhenRefill() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = 0;

        operationService.refill(accountId, accountAmount);
    }

    @Test(expected = LimitMoneyException.class)
    public void shouldThrowExceptionLimitLimitLowMoneyWhenTransfer() throws Exception {
        long accountId1 = ThreadLocalRandom.current().nextLong();
        long accountId2 = ThreadLocalRandom.current().nextLong();
        long toTransfer = 0;

        operationService.transfer(accountId1, accountId2, toTransfer);
    }

    @Test(expected = LimitMoneyException.class)
    public void shouldThrowExceptionLimitUpMoneyWhenWithdraw() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = 1000001;

        operationService.withdraw(accountId, accountAmount);
    }

    @Test(expected = LimitMoneyException.class)
    public void shouldThrowExceptionLimitLimitUpMoneyWhenRefill() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double accountAmount = 1000001;

        operationService.refill(accountId, accountAmount);
    }

    @Test(expected = LimitMoneyException.class)
    public void shouldThrowExceptionLimitLimitUpMoneyWhenTransfer() throws Exception {
        long accountId1 = ThreadLocalRandom.current().nextLong();
        long accountId2 = ThreadLocalRandom.current().nextLong();
        long toTransfer = 1000001;

        operationService.transfer(accountId1, accountId2, toTransfer);
    }
}
