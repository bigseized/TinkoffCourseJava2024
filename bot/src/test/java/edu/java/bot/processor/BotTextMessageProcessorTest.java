package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.UNSUPPORTED_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;

class BotTextMessageProcessorTest {

    @Test
    @DisplayName("Processor incorrect command test")
    public void processor_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(null);
        List<Command> commandsList =
            List.of(
                new ListCommand(),
                new StartCommand(),
                new TrackCommand(),
                new UntrackCommand()
            );
        BotMessageProcessor botMessageProcessor = new BotTextMessageProcessor(commandsList);
        SendMessage message = botMessageProcessor.process(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(UNSUPPORTED_COMMAND);
    }

}
