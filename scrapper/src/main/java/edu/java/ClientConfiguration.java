package edu.java;

import edu.java.repository.api.github.GitHubClient;
import edu.java.repository.api.stack_overflow.StackOverflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    @Bean
    public GitHubClient gitHubClientClass() {
        return new GitHubClient();
    }

    @Bean
    public StackOverflowClient getStackOverflowClientClass() {
        return new StackOverflowClient();
    }
}
