package edu.java.bot.sender;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender implements Sender {
    private final TelegramBot telegramBot;

    @Override
    public void sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message.parseMode(ParseMode.HTML));
    }
}
