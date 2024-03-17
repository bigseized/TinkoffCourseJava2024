package edu.java.bot.utilities;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandUtils {

    public static boolean isCorrectFormat(Update update, Command command) {
        String[] textParts = update.message().text().split(" ");
        return textParts.length == 2 && textParts[0].equals(command.command());
    }

    public static URI createLink(String link) {
        try {
            return new URI(link);
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
