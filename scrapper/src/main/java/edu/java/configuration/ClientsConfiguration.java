package edu.java.configuration;

import edu.java.repository.api.bot.BotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientsConfiguration {
    private final ClientsConfigurationProperties configurationProperties;

    @Bean
    public BotClient scrapperClient() {
        return new BotClient(configurationProperties.bot().baseUrl());
    }
}
