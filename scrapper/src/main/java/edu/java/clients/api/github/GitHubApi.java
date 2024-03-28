package edu.java.clients.api.github;

import edu.java.clients.api.github.dto.GitHubReposDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubApi {
    @GetExchange("/repos/{userName}/{reposName}")
    GitHubReposDTO fetchReposInfo(@PathVariable String userName, @PathVariable String reposName);
}
