package com.butomov.account.controller;

import com.butomov.account.api.exceptions.AccountAlreadyExists;
import com.butomov.account.api.exceptions.AccountNotFound;
import com.butomov.account.api.exceptions.UserNotFound;
import com.butomov.account.api.response.AccountResponse;
import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.AccountNotFoundException;
import com.butomov.account.exceptions.UserNotFoundException;
import com.butomov.account.model.Account;
import com.butomov.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PutMapping(value = "/{userId}")
    public ResponseEntity<AccountResponse> createAccount(@PathVariable String userId)
            throws AccountAlreadyExists, UserNotFound {

        try {
            final Account account = accountService.createAccount(UUID.fromString(userId));
            log.info(String.format("account %s created", account.getAccountId()));
            AccountResponse response = AccountResponse.builder()
                    .accountId(account.getAccountId())
                    .amount(account.getAmount())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (AccountExistsException e) {
            log.error(e.getMessage(), e);
            throw new AccountAlreadyExists();
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new UserNotFound();
        }
    }

    @GetMapping(value = "/{accountId}")
    public ResponseEntity<Double> getAmount(@PathVariable String accountId) throws AccountNotFound {
        try {
            return ResponseEntity.ok(accountService.getAmount(Long.valueOf(accountId)));
        } catch (AccountNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AccountNotFound();
        }
    }
}
