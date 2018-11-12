package com.butomov.account.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "username")
    private String username;

}
