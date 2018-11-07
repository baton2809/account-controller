package com.butomov.account.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Account already exists in the database")
public class AccountAlreadyExists extends Exception {
}
