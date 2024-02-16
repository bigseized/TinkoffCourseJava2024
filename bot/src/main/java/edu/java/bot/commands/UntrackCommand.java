package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_DESCRIPTION;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_INCORRECT_FORMAT;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_SUCCESS;

@Component
public class UntrackCommand implements Command {

    @Override
    public String command() {
        return UNTRACK_COMMAND;
    }

    @Override
    public String description() {
        return UNTRACK_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (update.message().text().split(" ").length == 2) {
            if (update.message().text().split(" ")[0].equals(command())) {
                //проверка ссылки на валидность
                return new SendMessage(chatId, UNTRACK_SUCCESS);
            }
            return new SendMessage(chatId, UNTRACK_INCORRECT_COMMAND);
        }
        return new SendMessage(chatId, UNTRACK_INCORRECT_FORMAT);
    }
}
