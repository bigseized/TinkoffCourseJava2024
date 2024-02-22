package edu.java.clients;

import edu.java.dto.GitHubReposDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static edu.java.parsers.GitHubLinkParse.parse;

@Component
public class GitHubClient {

    private final WebClient webClient;
    private final static String DEFAULT_GITHUB_URL = "https://api.github.com";

    public GitHubClient() {
        this(DEFAULT_GITHUB_URL);
    }

    public GitHubClient(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    Mono<GitHubReposDTO> fetchReposInfo(String link) {
        return webClient.get()
            .uri(parse(link))
            .retrieve()
            .bodyToMono(GitHubReposDTO.class);
    }
}
