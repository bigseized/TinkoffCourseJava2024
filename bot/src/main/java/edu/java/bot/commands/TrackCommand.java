package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import static edu.java.bot.utilities.ResponseMessages.TRACK_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.TRACK_DESCRIPTION;
import static edu.java.bot.utilities.ResponseMessages.TRACK_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.TRACK_INCORRECT_FORMAT;
import static edu.java.bot.utilities.ResponseMessages.TRACK_SUCCESS;

@Component
public class TrackCommand implements Command {

    @Override
    public String command() {
        return TRACK_COMMAND;
    }

    @Override
    public String description() {
        return TRACK_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (update.message().text().split(" ").length == 2) {
            if (update.message().text().split(" ")[0].equals(command())) {
                //проверка ссылки на валидность
                return new SendMessage(chatId, TRACK_SUCCESS);
            }
            return new SendMessage(chatId, TRACK_INCORRECT_COMMAND);
        }
        return new SendMessage(chatId, TRACK_INCORRECT_FORMAT);
    }
}
