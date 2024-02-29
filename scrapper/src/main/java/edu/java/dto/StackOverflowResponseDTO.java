package edu.java.dto;

import java.util.List;
import lombok.Data;

@Data
public class StackOverflowResponseDTO {
    private List<StackOverflowQuestionDTO> items;
}
