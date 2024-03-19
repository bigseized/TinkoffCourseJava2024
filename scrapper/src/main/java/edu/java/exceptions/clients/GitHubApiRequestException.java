package edu.java.exceptions.clients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class GitHubApiRequestException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
