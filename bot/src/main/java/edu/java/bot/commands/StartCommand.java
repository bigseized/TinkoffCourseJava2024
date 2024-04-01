package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import edu.java.bot.exceptions.ScrapperApiRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.START_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.START_DESCRIPTION;
import static edu.java.bot.utilities.ResponseMessages.WELCOME_MESSAGE;
import static edu.java.bot.utilities.ResponseMessages.WELCOME_TITLE;

@Service
@RequiredArgsConstructor
@Log4j2
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return START_COMMAND;
    }

    @Override
    public String description() {
        return START_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (!update.message().text().equals(command())) {
            return new SendMessage(chatId, DEFAULT_INCORRECT_COMMAND);
        }
        try {
            scrapperClient.postTelegramChat(chatId);
        } catch (ScrapperApiRequestException e) {
            if (e.getHttpStatus().equals(HttpStatus.CONFLICT)) {
                return sendAlreadyRegisteredMessage(chatId, e.getApiErrorResponse().exceptionMessage());
            }
            log.error("Unhandled exception from ScrapperApi", e);
        }

        String sendMessage = String.format(WELCOME_TITLE, update.message().from().firstName())
                             + WELCOME_MESSAGE;
        return new SendMessage(chatId, sendMessage);
    }

    private SendMessage sendAlreadyRegisteredMessage(long chatId, String message) {
        return new SendMessage(chatId, message);
    }
}
