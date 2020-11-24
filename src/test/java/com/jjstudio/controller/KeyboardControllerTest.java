package com.jjstudio.controller;

import com.jjstudio.config.SpringSecurityWebAuxTestConfig;
import com.jjstudio.dto.keyboard.CreateKeyboardRequest;
import com.jjstudio.resource.KeyboardRepository;
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

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class KeyboardControllerTest extends BaseControllerTest {

    @MockBean
    KeyboardRepository keyboardRepository;

    @Test
    @WithUserDetails("freeuser@gmail.com")
    public void givenInvalidNonExistentKeyMapping_whenCreateKeyboard_thenExpect400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/me/keyboards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getInvalidNonExistentKeyMapping())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid keyboard mapping configuration")));
    }

    private CreateKeyboardRequest getInvalidNonExistentKeyMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("-1", "12asdzx12");

        CreateKeyboardRequest request = new CreateKeyboardRequest();
        request.setName("Invalid Key");
        request.setMapping(mapping);

        return request;
    }

}
