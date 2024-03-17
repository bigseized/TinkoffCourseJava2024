package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import edu.java.bot.clients.api.scrapper.dto.request.AddLinkRequest;
import edu.java.bot.controllers.dto.response.ApiErrorResponse;
import edu.java.bot.exceptions.ScrapperApiRequestException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import static edu.java.bot.utilities.CommandUtils.createLink;
import static edu.java.bot.utilities.CommandUtils.isCorrectFormat;
import static edu.java.bot.utilities.ResponseMessages.INCORRECT_LINK;
import static edu.java.bot.utilities.ResponseMessages.TRACK_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.TRACK_DESCRIPTION;
import static edu.java.bot.utilities.ResponseMessages.TRACK_INCORRECT_FORMAT;
import static edu.java.bot.utilities.ResponseMessages.TRACK_SUCCESS;

@Service
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperClient scrapperClient;

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

        if (!isCorrectFormat(update, this)) {
            return new SendMessage(chatId, TRACK_INCORRECT_FORMAT);
        }

        String link = update.message().text().split(" ")[1];
        URI uriLink = createLink(link);
        if (uriLink == null) {
            return new SendMessage(chatId, INCORRECT_LINK);
        }

        try {
            scrapperClient.postLinks(chatId, new AddLinkRequest(uriLink));
        } catch (ScrapperApiRequestException e) {
            ApiErrorResponse apiErrorResponse = e.getApiErrorResponse();
            String errorMessage = apiErrorResponse.exceptionMessage();
            if (e.getHttpStatus().equals(HttpStatus.NOT_FOUND)) {
                errorMessage += "\nВведите /start для регистрации чата";
            }
            return new SendMessage(chatId, errorMessage);
        }
        return new SendMessage(chatId, TRACK_SUCCESS);
    }
}
