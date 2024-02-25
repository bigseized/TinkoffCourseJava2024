package edu.java.bot.utilities;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandUtils {

    public static SendMessage handleMessage(
        Update update,
        Command command,
        String successMessage,
        String incorrectCommandMessage,
        String incorrectFormatMessage
    ) {
        long chatId = command.getChatId(update);
        String[] textParts = update.message().text().split(" ");
        if (textParts.length == 2 && textParts[0].equals(command.command())) {
            return new SendMessage(chatId, successMessage);
        } else if (textParts.length == 2) {
            return new SendMessage(chatId, incorrectCommandMessage);
        } else {
            return new SendMessage(chatId, incorrectFormatMessage);
        }
    }
}
