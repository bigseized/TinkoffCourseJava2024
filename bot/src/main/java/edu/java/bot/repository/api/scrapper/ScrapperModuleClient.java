package edu.java.bot.repository.api.scrapper;

import edu.java.bot.controllers.dto.response.ApiErrorResponse;
import edu.java.bot.exceptions.ScrapperApiRequestException;
import edu.java.bot.repository.api.scrapper.dto.request.AddLinkRequest;
import edu.java.bot.repository.api.scrapper.dto.request.RemoveLinkRequest;
import edu.java.bot.repository.api.scrapper.dto.response.LinkResponse;
import edu.java.bot.repository.api.scrapper.dto.response.ListLinksResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Log4j2
@Component
public class ScrapperModuleClient {
    private final ScrapperModuleService service;

    public ScrapperModuleClient() {
        this("http://localhost:8080");
    }

    public ScrapperModuleClient(String baseUrl) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(
                HttpStatusCode::isError,
                response -> response.toEntity(ApiErrorResponse.class)
                    .map(responseEntity -> new ScrapperApiRequestException(responseEntity.getBody()))
            )
            .baseUrl(baseUrl)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(ScrapperModuleService.class);
    }

    public void postTelegramChat(long id) {
        try {
            service.postTgChat(id);
        } catch (ScrapperApiRequestException e) {
            log.error(e.getApiErrorResponse().code() + e.getApiErrorResponse().exceptionMessage());
        }
    }

    public void deleteTelegramChat(long id) {
        try {
            service.deleteTgChat(id);
        } catch (ScrapperApiRequestException e) {
            log.error(e.getApiErrorResponse().code() + e.getApiErrorResponse().exceptionMessage());
        }
    }

    public ListLinksResponse getLinks(long id) {
        try {
            return service.getLinks(id);
        } catch (ScrapperApiRequestException e) {
            log.error(e.getApiErrorResponse().code() + e.getApiErrorResponse().exceptionMessage());
        }
        return null;
    }

    public LinkResponse postLinks(long id, AddLinkRequest addLinkRequest) {
        try {
            return service.postLink(id, addLinkRequest);
        } catch (ScrapperApiRequestException e) {
            log.error(e.getApiErrorResponse().code() + e.getApiErrorResponse().exceptionMessage());
        }
        return null;
    }

    public LinkResponse deleteLinks(long id, RemoveLinkRequest removeLinkRequest) {
        try {
            return service.deleteLink(id, removeLinkRequest);
        } catch (ScrapperApiRequestException e) {
            log.error(e.getApiErrorResponse().code() + e.getApiErrorResponse().exceptionMessage());
        }
        return null;
    }
}
