package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.START_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StartCommandTest {
    ScrapperClient scrapperClient = mock(ScrapperClient.class);

    private static final String correctResponse = """
        Привет, User!
        <b>ReminderBot</b> - ваш главный помощник в мониторинге изменений!
        Просто введите <b>/track</b> <i>{ссылка}</i> и бот сделает вашу жизнь проще!
        Для просмотра всех команд введите <b>/help</b>.
        """;

    @Test
    @DisplayName("Correct /start command test")
    public void startCommand_correctCommand_test() {
        Update mockUpdate = createMockUpdate(START_COMMAND);
        Command command = new StartCommand(scrapperClient);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(correctResponse);
    }

    @Test
    @DisplayName("Incorrect /start command test")
    public void startCommand_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(START_COMMAND + "3");
        Command command = new StartCommand(scrapperClient);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(DEFAULT_INCORRECT_COMMAND);
    }
}
