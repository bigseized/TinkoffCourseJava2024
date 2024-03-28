package edu.java.bot.configuration;

import edu.java.bot.clients.api.scrapper.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientsConfiguration {
    private final ClientsConfigurationProperties configurationProperties;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient(configurationProperties.scrapper().baseUrl());
    }
}
