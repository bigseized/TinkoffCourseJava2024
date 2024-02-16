package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utilities.ResponseMessages;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.DUMMY_LINKS;
import static edu.java.bot.utilities.ResponseMessages.LIST_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.LIST_DESCRIPTION;

@Component
public class ListCommand implements Command {

    @Override
    public String command() {
        return LIST_COMMAND;
    }

    @Override
    public String description() {
        return LIST_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (update.message().text().equals(command())) {
            // получение ссылок на отслеживаемые ресурсы
            String sendMessage = buildListOfLinks(DUMMY_LINKS);
            return new SendMessage(chatId, sendMessage).disableWebPagePreview(true);
        }
        return new SendMessage(chatId, DEFAULT_INCORRECT_COMMAND);
    }

    private String buildListOfLinks(List<String> links) {
        return ResponseMessages.LIST_TITLE
            + IntStream.range(1, links.size() + 1)
                .mapToObj(index -> "*" + index + "*" + ": " + links.get(index - 1))
                .collect(Collectors.joining("\n"));
    }
}
