package edu.java.clients.api.stack_overflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class StackOverflowQuestionDTO {

    @JsonProperty("last_activity_date")
    private OffsetDateTime updateTime;

    @JsonProperty("title")
    private String questionText;
}
