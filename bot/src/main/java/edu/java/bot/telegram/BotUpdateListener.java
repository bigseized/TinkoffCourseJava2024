package edu.java.bot.telegram;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.telegram.processor.BotMessageProcessor;
import edu.java.bot.telegram.processor.BotTextMessageProcessor;
import edu.java.bot.telegram.sender.BotTextMessageSender;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BotUpdateListener implements UpdatesListener {
    private final BotMessageProcessor botMessageProcessor;
    private final BotTextMessageSender botTextMessageSender;

    public BotUpdateListener(List<Command> commands, BotTextMessageSender botTextMessageSender) {
        this.botTextMessageSender = botTextMessageSender;
        this.botMessageProcessor = new BotTextMessageProcessor(commands);
    }

    @Override
    public int process(List<Update> updates) {
        for (var update : updates) {
            // проверка на то, что пришло новое сообщение, а не отредактированное старое
            if (update.editedMessage() == null) {
                botTextMessageSender.sendMessage(botMessageProcessor.process(update));
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
