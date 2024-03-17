package edu.java.clients.api.github;

import edu.java.clients.api.github.dto.GitHubEventsDTO;
import edu.java.clients.api.github.dto.GitHubReposDTO;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubApi {
    @GetExchange("/repos/{userName}/{reposName}")
    GitHubReposDTO fetchReposInfo(@PathVariable String userName, @PathVariable String reposName);

    @GetExchange("/repos/{userName}/{reposName}/events?per_page=1")
    List<GitHubEventsDTO> fetchEventInfo(@PathVariable String userName, @PathVariable String reposName);
}
