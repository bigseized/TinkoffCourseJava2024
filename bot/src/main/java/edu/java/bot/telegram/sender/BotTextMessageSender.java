package edu.java.bot.telegram.sender;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotTextMessageSender {
    private final TelegramBot telegramBot;

    public void sendMessage(SendMessage message) {
        telegramBot.execute(message.parseMode(ParseMode.Markdown));
    }
}
