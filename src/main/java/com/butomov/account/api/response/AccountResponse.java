package com.butomov.account.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountResponse {

    @JsonProperty(value = "account_id")
    private Long accountId;

    @JsonProperty(value = "amount")
    private Double amount;
}
