package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.HELP_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;

class HelpCommandTest {

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
                new ListCommand(),
                new StartCommand(),
                new TrackCommand(),
                new UntrackCommand()
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
                new ListCommand(),
                new StartCommand(),
                new TrackCommand(),
                new UntrackCommand()
            );
        Command command = new HelpCommand(commandsList);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(DEFAULT_INCORRECT_COMMAND);
    }
}
