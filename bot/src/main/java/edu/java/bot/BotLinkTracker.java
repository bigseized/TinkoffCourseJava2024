package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.configuration.ApplicationConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotLinkTracker {
    private final UpdatesListener updatesListener;
    private final TelegramBot telegramBot;
    @PostConstruct
    public void start() {
        telegramBot.setUpdatesListener(updatesListener);
    }
}
