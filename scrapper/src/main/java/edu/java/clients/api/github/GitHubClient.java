package edu.java.clients.api.github;

import edu.java.clients.api.github.dto.GitHubReposDTO;
import edu.java.exceptions.clients.BotApiRequestException;
import edu.java.exceptions.clients.GitHubApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RequiredArgsConstructor
public class GitHubClient {

    private final GitHubApi service;
    @Value("${authorization-tokens.github}")
    private String authToken;

    public GitHubClient(String baseUrl) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(
                HttpStatusCode::isError,
                response -> response.toEntity(String.class)
                    .map(responseEntity -> new GitHubApiRequestException(
                        responseEntity.getBody(),
                        (HttpStatus) responseEntity.getStatusCode()
                    ))
            )
            .defaultHeader("Authorization", authToken)
            .baseUrl(baseUrl)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(GitHubApi.class);
    }

    public GitHubReposDTO fetchReposInfo(String userName, String reposName) throws BotApiRequestException {
        return service.fetchReposInfo(userName, reposName);
    }
}
