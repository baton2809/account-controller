package com.butomov.account.service;

import com.butomov.account.exceptions.UserExistsException;
import com.butomov.account.model.User;
import com.butomov.account.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateUserCorrectWhenNoExists() throws UserExistsException {
        final User user = mock(User.class);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        Assert.assertEquals(user, createdUser);
    }

    @Test(expected = UserExistsException.class)
    public void shouldThrowExceptionWhenUserAlreadyExists() throws UserExistsException {
        final User user = mock(User.class);
        when(userRepository.findByName(user.getName())).thenReturn(user);

        userService.createUser(user);
    }
}
