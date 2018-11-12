package com.butomov.account.controller;

import com.butomov.account.api.request.RefillRequest;
import com.butomov.account.api.request.TransferDetails;
import com.butomov.account.service.AccountService;
import com.butomov.account.service.OperationService;
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

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(OperationController.class)
public class OperationControllerTest {

    public static final String TEST = "Test";
    @MockBean
    private OperationService operationService;

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
    public void shouldRefillCorrectly() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double amount = 777d;
        RefillRequest refillRequest = RefillRequest.builder().accountId(accountId).amount(amount).build();
        when(operationService.refill(accountId, amount)).thenReturn(0d);

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/operations/refill")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsBytes(refillRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

    }

    @Test
    public void shouldWithdrawCorrectly() throws Exception {
        long accountId = ThreadLocalRandom.current().nextLong();
        double amount = 777d;
        RefillRequest refillRequest = RefillRequest.builder().accountId(accountId).amount(amount).build();
        when(operationService.withdraw(accountId, amount)).thenReturn(0d);

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/operations/withdraw")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsBytes(refillRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldTransferCorrectly() throws Exception {
        long accountId1 = ThreadLocalRandom.current().nextLong();
        long accountId2 = ThreadLocalRandom.current().nextLong();
        double amount = 777d;
        TransferDetails transferDetails = TransferDetails.builder()
                .senderId(accountId1)
                .payeeId(accountId2)
                .amount(amount)
                .build();
        when(operationService.transfer(accountId1, accountId2, amount)).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/operations/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsBytes(transferDetails))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }
}
