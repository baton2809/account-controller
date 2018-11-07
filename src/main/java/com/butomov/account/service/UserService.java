package com.butomov.account.service;

import com.butomov.account.domain.User;
import com.butomov.account.exceptions.UserExistsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    /**
     * Creates a User object
     *
     * @return
     */
    User createUser(User user) throws UserExistsException;

    User getUser(UUID userId);

    User getUser(String username);
}
