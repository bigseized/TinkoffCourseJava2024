package edu.java.clients.api.stack_overflow;

import edu.java.clients.api.stack_overflow.dto.StackOverflowQuestionDTO;
import edu.java.exceptions.clients.BotApiRequestException;
import edu.java.exceptions.clients.StackOverflowApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class StackOverflowClient {
    private final StackOverflowApi service;

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

    public StackOverflowQuestionDTO fetchQuestionsInfo(String id) throws BotApiRequestException {
        return service.fetchQuestionsInfo(id).getItems().stream().findAny().orElse(null);
    }
}
