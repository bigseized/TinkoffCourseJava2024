package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static edu.java.bot.utilities.ResponseMessages.UNSUPPORTED_COMMAND;

@Component
@RequiredArgsConstructor
public class MessageProcessor implements Processor {
    private final List<Command> commands;

    @Override
    public SendMessage process(Update update) {
        long chatId = update.message().chat().id();
        if (update.message().text() != null) {
            for (var command : commands) {
                if (command.supports(update)) {
                    return command.handle(update);
                }
            }
        }
        return new SendMessage(chatId, UNSUPPORTED_COMMAND);
    }
}
