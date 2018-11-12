package com.butomov.account.controller;

import com.butomov.account.api.exceptions.IllegalRequest;
import com.butomov.account.api.exceptions.UserAlreadyExists;
import com.butomov.account.api.request.UserRequest;
import com.butomov.account.api.response.UserResponse;
import com.butomov.account.exceptions.UserExistsException;
import com.butomov.account.model.User;
import com.butomov.account.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user)
            throws IllegalRequest, UserAlreadyExists {

        validateRequest(user);
        try {
            User newUser = User.builder()
                    .name(user.getUsername())
                    .password(user.getPassword())
                    .build();

            userService.createUser(newUser);
            log.info(String.format("user %s created", user.getUsername()));

            UserResponse response = UserResponse.builder()
                    .userId(String.valueOf(newUser.getUserId()))
                    .username(newUser.getName())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            log.error(e.getMessage(), e);
            throw new UserAlreadyExists();
        }
    }

    private void validateRequest(UserRequest user) throws IllegalRequest {
        if (isNull(user.getUsername()) || user.getUsername().isEmpty() ||
                isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            log.error("user validation fails");
            throw new IllegalRequest();
        }
    }
}
