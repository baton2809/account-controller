package com.butomov.account.service;

import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.UserNotFoundException;
import com.butomov.account.model.Account;
import com.butomov.account.model.User;
import com.butomov.account.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAccountCorrectly() throws Exception {
        final UUID userId = UUID.randomUUID();
        final User user = mock(User.class);
        when(userService.getUser(userId)).thenReturn(user);

        Account createdAccount = accountService.createAccount(userId);

        Assert.assertEquals(user, createdAccount.getUser());
        Assert.assertEquals(0d, createdAccount.getAmount(), 0.0001);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionWhenUserNotFound() throws Exception {
        accountService.createAccount(UUID.randomUUID());
    }

    @Test(expected = AccountExistsException.class)
    public void shouldThrowExceptionWhenAccountAlreadyExists() throws Exception {
        final UUID userId = UUID.randomUUID();
        when(userService.getUser(userId)).thenReturn(mock(User.class));
        when(accountRepository.findByUserUserId(userId)).thenReturn(mock(Account.class));

        accountService.createAccount(userId);
    }
}
