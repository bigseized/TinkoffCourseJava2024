package edu.java.bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.processor.MessageProcessor;
import edu.java.bot.processor.Processor;
import edu.java.bot.sender.Sender;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BotUpdateListener implements UpdatesListener {
    private final Processor processor;
    private final Sender messageSender;

    public BotUpdateListener(List<Command> commands, Sender messageSender) {
        this.messageSender = messageSender;
        this.processor = new MessageProcessor(commands);
    }

    @Override
    public int process(List<Update> updates) {
        for (var update : updates) {
            if (update.editedMessage() == null) {
                messageSender.sendMessage(processor.process(update));
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
