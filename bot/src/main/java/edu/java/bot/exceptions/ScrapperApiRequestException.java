package edu.java.bot.exceptions;

import edu.java.bot.controllers.dto.response.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScrapperApiRequestException extends RuntimeException {
    private final ApiErrorResponse apiErrorResponse;

    public String getShortErrorMessage() {
        return "Status code: " + apiErrorResponse.code() + "; Message: " + apiErrorResponse.exceptionMessage();
    }
}

