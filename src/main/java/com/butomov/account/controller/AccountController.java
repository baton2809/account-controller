package com.butomov.account.controller;

import com.butomov.account.api.exceptions.AccountAlreadyExists;
import com.butomov.account.api.exceptions.UserNotFound;
import com.butomov.account.api.response.AccountResponse;
import com.butomov.account.domain.Account;
import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.UserNotFoundException;
import com.butomov.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AccountController extends AbstractAPI {

    @Autowired
    private AccountService accountService;


    @PutMapping(value = "/accounts/{userId}")
    public ResponseEntity<AccountResponse> createAccount(@PathVariable String userId) throws Exception {

        try {
            Account account = accountService.createAccount(UUID.fromString(userId));

            AccountResponse response = new AccountResponse();
            response.setAccountId(account.getAccountId());
            response.setAmount(account.getAmount());
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (AccountExistsException e) {
            throw new AccountAlreadyExists();
        }catch (UserNotFoundException e) {
            throw new UserNotFound();
        }
    }

    @GetMapping(value = "/accounts/{userId}")
    public ResponseEntity<Double> getAmount(@PathVariable String userId) {
        return ResponseEntity.ok(accountService.getAmount(UUID.fromString(userId)));
    }
}
