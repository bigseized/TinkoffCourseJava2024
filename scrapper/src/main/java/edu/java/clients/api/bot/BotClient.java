package edu.java.clients.api.bot;

import edu.java.clients.api.bot.dto.LinkUpdateRequest;
import edu.java.controllers.dto.response.ApiErrorResponse;
import edu.java.exceptions.clients.BotApiRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Log4j2
public class BotClient {
    private final BotApi service;

    public BotClient(String baseUrl) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(
                HttpStatusCode::isError,
                response -> response.toEntity(ApiErrorResponse.class)
                    .map(responseEntity -> new BotApiRequestException(
                        responseEntity.getBody(),
                        (HttpStatus) responseEntity.getStatusCode()
                    ))
            )
            .baseUrl(baseUrl)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(BotApi.class);
    }

    @Retryable(
        maxAttemptsExpression = "#{@retry.maxAttempts()}",
        backoff = @Backoff(
            delayExpression = "#{@retry.backoff.delay()}",
            maxDelayExpression = "#{@retry.backoff.maxDelay()}",
            multiplierExpression = "#{@retry.backoff.multiplier()}"
        )
    )
    public void updateBot(LinkUpdateRequest linkUpdateRequest) throws BotApiRequestException {
        service.updateBot(linkUpdateRequest);
    }
}
