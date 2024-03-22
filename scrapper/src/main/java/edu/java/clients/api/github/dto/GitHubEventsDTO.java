package edu.java.clients.api.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubEventsDTO {
    @JsonProperty("type")
    private String type;
}
