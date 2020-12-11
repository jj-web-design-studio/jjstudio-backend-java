package com.jjstudio.controller;

import com.jjstudio.config.SpringSecurityWebAuxTestConfig;
import com.jjstudio.config.auth.UserPrincipal;
import com.jjstudio.dto.AuthenticationRequest;
import com.jjstudio.model.User;
import com.jjstudio.service.MyUserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class AuthControllerTest extends BaseControllerTest {

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    MyUserDetailsServiceImpl userDetailsService;

    @Test
    public void givenInvalidUserCredentials_whenLogin_thenExpect401() throws Exception {
        when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getAuthenticationRequest())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenValidUserCredentials_whenLogin_thenExpect401() throws Exception {
        when(authenticationManager.authenticate(any()))
                .thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(new UserPrincipal(getUser(), new ArrayList<>()));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getAuthenticationRequest())))
                .andExpect(status().isOk());
    }

    private AuthenticationRequest getAuthenticationRequest() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("any@gmail.com");
        request.setPassword("any");
        return request;
    }

    private User getUser() {
        User user = new User();
        user.setEmail("validuser@gmail.com");
        return user;
    }
}
