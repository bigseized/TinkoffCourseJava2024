package edu.java.bot.configuration.clients;

import edu.java.bot.clients.api.scrapper.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@RequiredArgsConstructor
public class ClientsConfiguration {
    private final ClientsConfigurationProperties configurationProperties;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient(configurationProperties.scrapper().baseUrl());
    }
}
