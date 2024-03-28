package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import edu.java.bot.clients.api.scrapper.dto.request.RemoveLinkRequest;
import edu.java.bot.controllers.dto.response.ApiErrorResponse;
import edu.java.bot.exceptions.ScrapperApiRequestException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static edu.java.bot.utilities.CommandUtils.createLink;
import static edu.java.bot.utilities.CommandUtils.isCorrectFormat;
import static edu.java.bot.utilities.ResponseMessages.INCORRECT_LINK;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_DESCRIPTION;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_INCORRECT_FORMAT;
import static edu.java.bot.utilities.ResponseMessages.UNTRACK_SUCCESS;

@Service
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final ScrapperClient scrapperClient;

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

        if (!isCorrectFormat(update, this)) {
            return new SendMessage(chatId, UNTRACK_INCORRECT_FORMAT);
        }

        String link = update.message().text().split(" ")[1];
        URI uriLink = createLink(link);
        if (uriLink == null) {
            return new SendMessage(chatId, INCORRECT_LINK);
        }

        try {
            scrapperClient.deleteLinks(chatId, new RemoveLinkRequest(uriLink));
        } catch (ScrapperApiRequestException e) {
            ApiErrorResponse apiErrorResponse = e.getApiErrorResponse();
            return new SendMessage(chatId, apiErrorResponse.exceptionMessage());
        }
        return new SendMessage(chatId, UNTRACK_SUCCESS);
    }
}
