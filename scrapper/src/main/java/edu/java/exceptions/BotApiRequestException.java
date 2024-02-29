package edu.java.exceptions;

import edu.java.controllers.dto.response.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BotApiRequestException extends RuntimeException {
    private final ApiErrorResponse apiErrorResponse;
}
