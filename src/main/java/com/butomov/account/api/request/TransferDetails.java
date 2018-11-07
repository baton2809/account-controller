package com.butomov.account.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class TransferDetails {

    /**
     * Sender
     */
    @JsonProperty(value = "sender_id")
    @NonNull
    private Long senderId;

    /**
     * Receiver
     */
    @JsonProperty(value = "payee_id")
    @NonNull
    private Long payeeId;

    /**
     * Amount
     */
    @JsonProperty(value = "amount")
    @NonNull
    private Double amount;
}
