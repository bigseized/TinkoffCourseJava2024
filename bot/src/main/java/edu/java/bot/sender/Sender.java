package edu.java.bot.sender;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public interface Sender {
    void sendMessage(SendMessage message);
}
