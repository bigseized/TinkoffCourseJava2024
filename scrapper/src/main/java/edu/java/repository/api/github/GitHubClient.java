package edu.java.repository.api.github;

import edu.java.dto.GitHubReposDTO;
import org.springframework.web.reactive.function.client.WebClient;
import static edu.java.parsers.LinkParseUtil.parseGitHub;

public class GitHubClient {

    private final WebClient webClient;
    private final static String DEFAULT_GITHUB_URL = "https://api.github.com";

    public GitHubClient() {
        this(DEFAULT_GITHUB_URL);
    }

    public GitHubClient(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    public GitHubReposDTO fetchReposInfo(String link) {
        return webClient.get()
            .uri(parseGitHub(link))
            .retrieve()
            .bodyToMono(GitHubReposDTO.class)
            .block();
    }
}
