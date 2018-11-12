package com.butomov.account.service;

import com.butomov.account.exceptions.UserExistsException;
import com.butomov.account.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    User createUser(User user) throws UserExistsException;

    User getUser(UUID userId);

    User getUser(String username);
}
