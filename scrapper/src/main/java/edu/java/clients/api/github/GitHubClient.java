package edu.java.clients.api.github;

import edu.java.clients.api.github.dto.GitHubEventsDTO;
import edu.java.clients.api.github.dto.GitHubReposDTO;
import edu.java.exceptions.clients.GitHubApiRequestException;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RequiredArgsConstructor
@Log4j2
public class GitHubClient {

    private final GitHubApi service;
    private final HttpHeaders authorizationHeader = new HttpHeaders();
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
            .baseUrl(baseUrl)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(GitHubApi.class);

    }

    @PostConstruct
    private void setAuthorizationHeader() {
        authorizationHeader.setBearerAuth(authToken);
    }

    @Retry(name = "basic")
    public GitHubReposDTO fetchReposInfo(String userName, String reposName) throws GitHubApiRequestException {
        log.info("repos fetch init");
        return service.fetchReposInfo(userName, reposName, authorizationHeader);
    }

    @Retry(name = "basic")
    public GitHubEventsDTO fetchEventInfo(String userName, String reposName) throws GitHubApiRequestException {
        return service.fetchEventInfo(userName, reposName, authorizationHeader).getFirst();
    }
}
