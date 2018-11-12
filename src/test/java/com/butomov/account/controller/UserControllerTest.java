package com.butomov.account.controller;

import com.butomov.account.api.request.UserRequest;
import com.butomov.account.api.response.UserResponse;
import com.butomov.account.model.User;
import com.butomov.account.service.UserService;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    public static final String TEST = "Test";

    @MockBean
    private UserService userService;

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
    public void shouldCreateUserCorrectly() throws Exception {
        User user = User.builder().userId(UUID.randomUUID()).name(TEST).password(TEST).build();
        when(userService.createUser(user)).thenReturn(user);
        UserRequest userRequest = UserRequest.builder().username(TEST).password(TEST).build();

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsBytes(userRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(objectMapper.readValue(response.getContentAsByteArray(), UserResponse.class).getUsername(),
                is(TEST));
    }
}
