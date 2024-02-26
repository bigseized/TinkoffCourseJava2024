package edu.java.bot.services;

import edu.java.bot.controllers.dto.response.ApiErrorResponse;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BotUpdateExceptionsService {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequest(MethodArgumentNotValidException exception) {
        return ApiErrorResponse.builder()
            .description("Некорректные параметры запроса")
            .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
            .exceptionName(exception.getObjectName())
            .exceptionMessage(exception.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", ")))
            .stackTrace(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList())
            .build();
    }
}
