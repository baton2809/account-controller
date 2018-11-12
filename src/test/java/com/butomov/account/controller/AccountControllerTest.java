package com.butomov.account.controller;

import com.butomov.account.api.response.AccountResponse;
import com.butomov.account.model.Account;
import com.butomov.account.model.User;
import com.butomov.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    public static final String TEST = "Test";

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;
    private HttpHeaders headers;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
    }

    @Test
    public void shouldCreateAccountCorrectly() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = User.builder().name(TEST).password(TEST).build();
        long accountId = ThreadLocalRandom.current().nextLong();
        Account account = Account.builder().accountId(accountId).user(user).amount(777d).build();
        when(accountService.createAccount(userId)).thenReturn(account);

        MockHttpServletResponse response = mvc
                .perform(
                        MockMvcRequestBuilders.put("/accounts/{userId}", userId)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers)
                                .characterEncoding("utf-8"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(objectMapper.readValue(response.getContentAsByteArray(), AccountResponse.class).getAmount(),
                is(777d));
    }

}
