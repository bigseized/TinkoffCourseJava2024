package edu.java.clients.api.stack_overflow;

import edu.java.clients.api.stack_overflow.dto.StackOverflowQuestionDTO;
import edu.java.exceptions.clients.StackOverflowApiRequestException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@EnableRetry
@Log4j2
public class StackOverflowClient {
    private final StackOverflowApi service;

    @Value("${authorization-tokens.stack-overflow.access-token}")
    private String accessToken;
    @Value("${authorization-tokens.stack-overflow.key}")
    private String key;

    public StackOverflowClient(String baseUrl) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(
                HttpStatusCode::isError,
                response -> response.toEntity(String.class)
                    .map(responseEntity -> new StackOverflowApiRequestException(
                        responseEntity.getBody(),
                        (HttpStatus) responseEntity.getStatusCode()
                    ))
            )
            .baseUrl(baseUrl)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(StackOverflowApi.class);
    }

    @Retry(name = "basic")
    public StackOverflowQuestionDTO fetchQuestionsInfo(String id) throws StackOverflowApiRequestException {
        return service.fetchQuestionsInfo(id, accessToken, key).getItems().stream().findAny().orElse(null);
    }
}
