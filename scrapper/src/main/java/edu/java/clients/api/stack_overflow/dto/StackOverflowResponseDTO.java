package edu.java.clients.api.stack_overflow.dto;

import java.util.List;
import lombok.Data;

@Data
public class StackOverflowResponseDTO {
    private List<StackOverflowQuestionDTO> items;
}
