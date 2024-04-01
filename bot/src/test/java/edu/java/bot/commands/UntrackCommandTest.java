package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DUMMY_GITHUB;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_INCORRECT_FORMAT;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UntrackCommandTest {
    ScrapperClient scrapperClient = mock(ScrapperClient.class);

    @Test
    @DisplayName("Correct /untrack command test")
    public void untrackCommand_correctCommand_test() {
        Update mockUpdate = createMockUpdate(UNTRACK_COMMAND + " " + DUMMY_GITHUB);
        Command command = new UntrackCommand(scrapperClient);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(UNTRACK_SUCCESS);
    }

    @Test
    @DisplayName("Incorrect /untrack command test")
    public void untrackCommand_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(UNTRACK_COMMAND + "3");
        Command command = new UntrackCommand(scrapperClient);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(UNTRACK_INCORRECT_FORMAT);
    }

}
