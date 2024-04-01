package edu.java.exceptions.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class GitHubApiRequestException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
