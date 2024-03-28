package edu.java.exceptions.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class StackOverflowApiRequestException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatus httpStatus;
}
