package edu.java.bot.telegram.sender;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.service.annotation.HttpExchange;

@Component
@RequiredArgsConstructor
@HttpExchange
public class BotTextMessageSender {
    private final TelegramBot telegramBot;

    public void sendMessage(SendMessage message) {
        telegramBot.execute(message.parseMode(ParseMode.Markdown));
    }
}
