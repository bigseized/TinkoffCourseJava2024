package edu.java.bot.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final static String TEST_CORRECT_BODY = """
        {
          "id": 1,
          "url": "dsa",
          "description": "Some description",
          "tgChatIds": [43, 456]
        }
        """;

    private final static String TEST_INCORRECT_BODY = """
        {
          "id": -1,
          "url": "dsa",
          "description": "Some description",
          "tgChatIds": [43, 456]
        }
        """;

    @Test
    @DisplayName("Update: POST; Correct request")
    public void postUpdateCorrect() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TEST_CORRECT_BODY))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Update: POST; Incorrect request")
    public void postUpdateIncorrect() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TEST_INCORRECT_BODY))
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

}
