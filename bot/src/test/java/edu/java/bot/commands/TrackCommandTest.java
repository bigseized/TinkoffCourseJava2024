package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DUMMY_GITHUB;
import static edu.java.bot.utilities.ResponseMessages.TRACK_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.TRACK_INCORRECT_FORMAT;
import static edu.java.bot.utilities.ResponseMessages.TRACK_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TrackCommandTest {
    ScrapperClient scrapperClient = mock(ScrapperClient.class);

    @Test
    @DisplayName("Correct /track command test")
    public void trackCommand_correctCommand_test() {
        Update mockUpdate = createMockUpdate(TRACK_COMMAND + " " + DUMMY_GITHUB);
        Command command = new TrackCommand(scrapperClient);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(TRACK_SUCCESS);
    }

    @Test
    @DisplayName("Incorrect /track command test")
    public void trackCommand_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(TRACK_COMMAND + "3");
        Command command = new TrackCommand(scrapperClient);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(TRACK_INCORRECT_FORMAT);
    }

}
