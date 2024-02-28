package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TelegramBotConfig {
    private final ApplicationConfig applicationConfig;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(applicationConfig.telegramToken());
    }
}
