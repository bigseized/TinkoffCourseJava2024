package edu.java.bot.telegram.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public interface BotMessageProcessor {
    SendMessage process(Update update);
}
