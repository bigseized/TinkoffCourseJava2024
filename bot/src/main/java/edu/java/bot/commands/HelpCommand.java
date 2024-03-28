package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.HELP_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.HELP_DESCRIPTION;

@Service
@RequiredArgsConstructor
public class HelpCommand implements Command {

    public static final String DELIMITER = " - ";
    private final List<Command> commands;

    @Override
    public String command() {
        return HELP_COMMAND;
    }

    @Override
    public String description() {
        return HELP_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (!update.message().text().equals(command())) {
            return new SendMessage(chatId, DEFAULT_INCORRECT_COMMAND);
        }
        StringBuilder sendMessage = new StringBuilder();
        sendMessage.append(command()).append(DELIMITER).append(description()).append("\n");
        for (var command : commands) {
            sendMessage.append(command.command()).append(DELIMITER).append(command.description()).append("\n");
        }
        return new SendMessage(chatId, sendMessage.toString());
    }
}
