package edu.java.configuration;

import edu.java.clients.api.bot.BotClient;
import edu.java.clients.api.github.GitHubClient;
import edu.java.clients.api.stack_overflow.StackOverflowClient;
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
    public BotClient botClient() {
        return new BotClient(configurationProperties.bot().baseUrl());
    }

    @Bean
    public GitHubClient gitHubClientClass() {
        return new GitHubClient(configurationProperties.github().baseUrl());
    }

    @Bean
    public StackOverflowClient getStackOverflowClientClass() {
        return new StackOverflowClient(configurationProperties.stackOverflow().baseUrl());
    }
}
