package edu.java.clients.api.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class GitHubReposDTO {
    @JsonProperty("pushed_at")
    private OffsetDateTime updateTime;

    @JsonProperty("name")
    private String reposName;
}
