package com.butomov.account.controller;

import com.butomov.account.api.exceptions.AccountNotFound;
import com.butomov.account.api.exceptions.LimitMoneyExceeded;
import com.butomov.account.api.request.RefillRequest;
import com.butomov.account.api.request.TransferDetails;
import com.butomov.account.api.request.WithdrawRequest;
import com.butomov.account.api.response.AccountResponse;
import com.butomov.account.exceptions.AccountExistsException;
import com.butomov.account.exceptions.LimitMoneyException;
import com.butomov.account.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController extends AbstractAPI {

    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(value = "/refill")
    public ResponseEntity refillMoney(@RequestBody RefillRequest request)
            throws LimitMoneyExceeded, AccountNotFound {

        AccountResponse response = new AccountResponse();
        try {
            double totalAmount = operationService.refill(request.getAccountId(), request.getAmount());

            response.setAccountId(request.getAccountId());
            response.setAmount(totalAmount);

        } catch (LimitMoneyException e) {
            throw new LimitMoneyExceeded();
        } catch (AccountExistsException e) {
            throw new AccountNotFound();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity withdrawMoney(@RequestBody WithdrawRequest request)
            throws LimitMoneyExceeded, AccountNotFound {

        try {
            operationService.withdraw(request.getAccountId(), request.getAmount());
        } catch (LimitMoneyException e) {
            throw new LimitMoneyExceeded();
        } catch (AccountExistsException e) {
            throw new AccountNotFound();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity transferMoney(@RequestBody TransferDetails transferDetails)
            throws AccountNotFound, LimitMoneyExceeded {

        try {
            operationService.transfer(
                    transferDetails.getSenderId(),
                    transferDetails.getPayeeId(),
                    transferDetails.getAmount()
            );

        } catch (LimitMoneyException e) {
            throw new LimitMoneyExceeded();
        } catch (AccountExistsException e) {
            throw new AccountNotFound();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
