package edu.java.repository.api.bot;

import edu.java.controllers.dto.response.ApiErrorResponse;
import edu.java.exceptions.BotApiRequestException;
import edu.java.repository.api.bot.dto.LinkUpdateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
@Log4j2
public class BotModuleClient {
    private final BotUpdateService service;

    public BotModuleClient() {
        this("http://localhost:8090");
    }

    public BotModuleClient(String baseUrl) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(
                HttpStatusCode::isError,
                response -> response.toEntity(ApiErrorResponse.class)
                    .map(responseEntity -> new BotApiRequestException(responseEntity.getBody()))
            )
            .baseUrl(baseUrl)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(BotUpdateService.class);
        updateBot(null);
    }

    public void updateBot(LinkUpdateRequest linkUpdateRequest) {
        try {
            service.updateBot(linkUpdateRequest);
        } catch (BotApiRequestException e) {
            log.error(e.getApiErrorResponse().code() + e.getApiErrorResponse().exceptionMessage());
        }
    }
}
