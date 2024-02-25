package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.LIST_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;

//так как команда list пока возвращает определённый набор ссылок, тест придётся изменить
class ListCommandTest {

    public static final String correctResponse =
        """
            Отслеживаемые ресурсы:
            *1*: https://github.com/bigseized
            *2*: https://stackoverflow.com/questions""";

    @Test
    @DisplayName("Correct /list command test")
    public void listCommand_correctCommand_test() {
        Update mockUpdate = createMockUpdate(LIST_COMMAND);
        Command command = new ListCommand();
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(correctResponse);
    }

    @Test
    @DisplayName("Incorrect /list command test")
    public void listCommand_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(LIST_COMMAND + "3");
        Command command = new ListCommand();
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(DEFAULT_INCORRECT_COMMAND);
    }
}
