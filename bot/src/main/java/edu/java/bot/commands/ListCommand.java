package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import edu.java.bot.utilities.ResponseMessages;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.LIST_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.LIST_DESCRIPTION;
import static edu.java.bot.utilities.ResponseMessages.LIST_EMPTY;

@Service
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

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
        if (!update.message().text().equals(command())) {
            return new SendMessage(chatId, DEFAULT_INCORRECT_COMMAND);
        }
        List<String> listLink =
            scrapperClient.getLinks(chatId).links()
                .stream()
                .map(link -> link.url().toString())
                .toList();

        if (listLink.isEmpty()) {
            return new SendMessage(chatId, LIST_EMPTY);
        }
        String sendMessage = buildListOfLinks(listLink);
        return new SendMessage(chatId, sendMessage).disableWebPagePreview(true);
    }

    private String buildListOfLinks(List<String> links) {
        return ResponseMessages.LIST_TITLE
               + IntStream.range(1, links.size() + 1)
                   .mapToObj(index -> "<b>" + index + "</b>" + ": " + links.get(index - 1))
                   .collect(Collectors.joining("\n"));
    }
}
