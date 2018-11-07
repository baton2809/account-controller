package com.butomov.account.controller;

import com.butomov.account.api.exceptions.IllegalRequest;
import com.butomov.account.api.exceptions.UserAlreadyExists;
import com.butomov.account.api.request.UserRequest;
import com.butomov.account.api.response.UserResponse;
import com.butomov.account.domain.User;
import com.butomov.account.exceptions.UserExistsException;
import com.butomov.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@RestController
public class UserController extends AbstractAPI {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user)
            throws Exception {

        validateRequest(user);
        try {
            User newUser = new User();
            newUser.setName(user.getUsername());
            newUser.setPassword(user.getPassword());

            userService.createUser(newUser);

            UserResponse response = new UserResponse();
            response.setUserId(String.valueOf(newUser.getUserId()));
            response.setUsername(newUser.getName());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            throw new UserAlreadyExists();
        }
    }

    private void validateRequest(UserRequest user) throws IllegalRequest {
        if (isNull(user.getUsername()) || user.getUsername().isEmpty() ||
                isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            throw new IllegalRequest();
        }
    }
}
