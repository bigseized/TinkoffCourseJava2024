package edu.java.exceptions.clients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class StackOverflowApiRequestException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatus httpStatus;
}
