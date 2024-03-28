package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.HELP_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HelpCommandTest {
    ScrapperClient scrapperClient = mock(ScrapperClient.class);

    private static final String correctResponse = """
        /help - выводит описание всех команд
        /list - выводит список всех отслеживаемых ресурсов
        /start - запускает бота
        /track - добавляет новый ресурс для отслеживания
        /untrack - удаляет ресурс из списка отслеживаемых
        """;

    @Test
    @DisplayName("Correct /help command test")
    public void helpCommand_correctCommand_test() {
        Update mockUpdate = createMockUpdate(HELP_COMMAND);
        List<Command> commandsList =
            List.of(
                new ListCommand(scrapperClient),
                new StartCommand(scrapperClient),
                new TrackCommand(scrapperClient),
                new UntrackCommand(scrapperClient)
            );
        Command command = new HelpCommand(commandsList);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(correctResponse);
    }

    @Test
    @DisplayName("Incorrect /help command test")
    public void helpCommand_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(HELP_COMMAND + "3");
        List<Command> commandsList =
            List.of(
                new ListCommand(scrapperClient),
                new StartCommand(scrapperClient),
                new TrackCommand(scrapperClient),
                new UntrackCommand(scrapperClient)
            );
        Command command = new HelpCommand(commandsList);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(DEFAULT_INCORRECT_COMMAND);
    }
}
