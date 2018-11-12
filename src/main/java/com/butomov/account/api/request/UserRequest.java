package com.butomov.account.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class UserRequest {

    @JsonProperty(value = "username")
    @NonNull
    private String username;

    @JsonProperty(value = "password")
    private String password;

}
