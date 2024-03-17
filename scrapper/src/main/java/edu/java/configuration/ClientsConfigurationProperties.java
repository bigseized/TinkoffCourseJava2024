package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(value = "clients", ignoreUnknownFields = false)
public record ClientsConfigurationProperties(
    @Validated
    Bot bot,
    @Validated
    GitHub github,
    @Validated
    StackOverflow stackOverflow
) {
    public record Bot(@URL @NotNull String baseUrl) {
    }

    public record GitHub(@URL @NotNull String baseUrl) {
    }

    public record StackOverflow(@URL @NotNull String baseUrl) {
    }
}
