package edu.java.exceptions;

import edu.java.controllers.dto.response.ApiErrorResponse;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ApiErrorResponseException extends RuntimeException {

    private final String message;
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    public ApiErrorResponse toApiErrorResponse(ApiErrorResponseException exception) {
        return ApiErrorResponse.builder()
            .description(exception.description)
            .code(String.valueOf(exception.httpStatus.value()))
            .exceptionName(exception.httpStatus.name())
            .exceptionMessage(exception.message)
            .stackTrace(Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }
}
