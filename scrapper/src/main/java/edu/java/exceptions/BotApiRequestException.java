package edu.java.exceptions;

import edu.java.controllers.dto.response.ApiErrorResponse;
import lombok.Data;

@Data
public class BotApiRequestException extends RuntimeException {
    private final ApiErrorResponse apiErrorResponse;
}
