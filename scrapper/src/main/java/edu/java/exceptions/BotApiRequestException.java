package edu.java.exceptions;

import edu.java.controllers.dto.response.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BotApiRequestException extends RuntimeException {
    private final ApiErrorResponse apiErrorResponse;
    private final HttpStatus httpStatus;

    public String getShortErrorMessage() {
        return "Status code: " + apiErrorResponse.code() + "; Message: " + apiErrorResponse.exceptionMessage();
    }
}
