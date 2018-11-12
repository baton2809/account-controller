package com.butomov.account.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class TransferDetails {

    @JsonProperty(value = "sender_id")
    @NonNull
    private Long senderId;

    @JsonProperty(value = "payee_id")
    @NonNull
    private Long payeeId;

    @JsonProperty(value = "amount")
    @NonNull
    private Double amount;
}
