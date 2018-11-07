package com.butomov.account.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.DecimalMin;

@Data
public class WithdrawRequest {

    @JsonProperty(value = "account_id")
    @NonNull
    private Long accountId;

    @JsonProperty(value = "amount")
    @DecimalMin(value = "1")
    private Double amount;
}
