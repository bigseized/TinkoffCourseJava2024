package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class GitHubReposDTO {
    @JsonProperty("updated_at")
    private OffsetDateTime updateTime;

    @JsonProperty("name")
    private String reposName;
}
