package edu.java.configuration.clients;

import edu.java.clients.api.bot.BotClient;
import edu.java.clients.api.github.GitHubClient;
import edu.java.clients.api.stack_overflow.StackOverflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@RequiredArgsConstructor
public class ClientsConfiguration {
    private final ClientsConfigurationProperties configurationProperties;

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
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
