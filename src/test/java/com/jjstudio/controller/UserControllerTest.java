package com.jjstudio.controller;

import com.jjstudio.config.SpringSecurityWebAuxTestConfig;
import com.jjstudio.dto.user.CreateUserRequest;
import com.jjstudio.model.User;
import com.jjstudio.resource.UserRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class UserControllerTest extends BaseControllerTest {

    @MockBean
    UserRepository userRepository;

    @Test
    @WithUserDetails("freeuser@gmail.com")
    public void givenEmailAlreadyExists_whenCreateUser_thenExpect400() throws Exception {
        when(userRepository.findByEmail("loditjones@gmail.com")).thenReturn(new User());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getCreateUserRequest(null))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("A user with email loditjones@gmail.com already exists.")));
    }

    @Test
    @WithUserDetails("freeuser@gmail.com")
    public void givenInvalidPassword_whenCreateUser_thenExpect400() throws Exception {
        when(userRepository.findByEmail("loditjones@gmail.com")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getCreateUserRequest(null))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Password is unsatisfactory.")));
    }

    @Test
    @WithUserDetails("freeuser@gmail.com")
    public void givenValidRequest_whenCreateUser_thenExpect200() throws Exception {
        when(userRepository.findByEmail("loditjones@gmail.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(getSavedUser());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getCreateUserRequest("pwd123"))))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("5f9a695e5a1df479d9c1de85")));
    }

    private CreateUserRequest getCreateUserRequest(String password) {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("loditjones@gmail.com");
        request.setPassword(password != null ? password : "pwd");
        request.setFirstName("Lodit");
        request.setLastName("Jones");
        return request;
    }

    private User getSavedUser() {
        User user = new User();
        user.setId(new ObjectId("5f9a695e5a1df479d9c1de85"));
        return user;
    }
}
