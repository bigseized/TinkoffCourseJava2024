package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.START_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;

class StartCommandTest {
    private static final String correctResponse = """
        Привет, User!
        *ReminderBot* - ваш главный помощник в мониторинге изменений!
        Просто введите */track {*_ссылка_*}* и бот сделает вашу жизнь проще!
        Для просмотра всех команд введите */help*""";

    @Test
    @DisplayName("Correct /start command test")
    public void startCommand_correctCommand_test() {
        Update mockUpdate = createMockUpdate(START_COMMAND);
        Command command = new StartCommand();
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(correctResponse);
    }

    @Test
    @DisplayName("Incorrect /start command test")
    public void startCommand_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(START_COMMAND + "3");
        Command command = new StartCommand();
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(DEFAULT_INCORRECT_COMMAND);
    }
}
