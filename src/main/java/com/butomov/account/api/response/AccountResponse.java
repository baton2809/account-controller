package com.butomov.account.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountResponse {

    @JsonProperty(value = "account_id")
    private Long accountId;

    @JsonProperty(value = "amount")
    private Double amount;
}
