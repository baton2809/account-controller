package com.butomov.account.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponse {

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "username")
    private String username;

}
