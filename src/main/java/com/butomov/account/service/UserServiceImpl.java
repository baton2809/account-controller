package com.butomov.account.service;

import com.butomov.account.exceptions.UserExistsException;
import com.butomov.account.model.User;
import com.butomov.account.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws UserExistsException {
        log.info("creating user...");
        if (nonNull(getUser(user.getName()))) {
            log.error("user exists already...");
            throw new UserExistsException();
        }
        return userRepository.save(user);
    }

    @Override
    public User getUser(UUID userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User getUser(String name) {
        return userRepository.findByName(name);
    }
}
