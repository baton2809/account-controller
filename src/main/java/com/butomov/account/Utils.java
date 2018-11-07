package com.butomov.account;

import java.util.UUID;

public class Utils {

    public static UUID createRandomId4User(String username) {
        return UUID.fromString(username);
    }
}
