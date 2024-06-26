package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.telegram.BotUpdateListener;
import edu.java.bot.telegram.sender.BotTextMessageSender;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdateEditedMessage;
import static edu.java.bot.utilities.ResponseMessages.START_COMMAND;
import static org.mockito.Mockito.mock;

class BotUpdateListenerTest {
    ScrapperClient scrapperClient = mock(ScrapperClient.class);

    @Test
    @DisplayName("Process work with new messages")
    public void processWorkWith_newMessage() {
        Update mockUpdate = createMockUpdate(START_COMMAND);
        List<Command> commandsList =
            List.of(
                new ListCommand(scrapperClient),
                new StartCommand(scrapperClient),
                new TrackCommand(scrapperClient),
                new UntrackCommand(scrapperClient)
            );
        BotTextMessageSender sender = mock(BotTextMessageSender.class);
        BotUpdateListener listener = new BotUpdateListener(commandsList, sender);
        listener.process(List.of(mockUpdate));
        Mockito.verify(sender, Mockito.times(1))
            .sendMessage(Mockito.any());
    }

    @Test
    @DisplayName("Process doesn`t work with edited messages")
    public void processWorkWith_editedMessage() {
        Update mockUpdate = createMockUpdateEditedMessage(START_COMMAND);
        List<Command> commandsList =
            List.of(
                new ListCommand(scrapperClient),
                new StartCommand(scrapperClient),
                new TrackCommand(scrapperClient),
                new UntrackCommand(scrapperClient)
            );
        BotTextMessageSender sender = mock(BotTextMessageSender.class);
        BotUpdateListener listener = new BotUpdateListener(commandsList, sender);
        listener.process(List.of(mockUpdate));
        Mockito.verify(sender, Mockito.times(0))
            .sendMessage(Mockito.any());
    }

}
