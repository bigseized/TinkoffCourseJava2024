package edu.java.bot.clients.api.scrapper;

import edu.java.bot.clients.api.scrapper.dto.request.AddLinkRequest;
import edu.java.bot.clients.api.scrapper.dto.request.RemoveLinkRequest;
import edu.java.bot.clients.api.scrapper.dto.response.LinkResponse;
import edu.java.bot.clients.api.scrapper.dto.response.ListLinksResponse;
import edu.java.bot.controllers.dto.response.ApiErrorResponse;
import edu.java.bot.exceptions.ScrapperApiRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Log4j2
public class ScrapperClient {
    private final ScrapperApi service;

    public ScrapperClient(String baseUrl) {
        WebClient webClient = WebClient.builder()
            .defaultStatusHandler(
                HttpStatusCode::isError,
                response -> response.toEntity(ApiErrorResponse.class)
                    .map(responseEntity -> new ScrapperApiRequestException(
                        responseEntity.getBody(),
                        (HttpStatus) responseEntity.getStatusCode()
                    ))
            )
            .baseUrl(baseUrl)
            .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(ScrapperApi.class);
    }

    public void postTelegramChat(long id) throws ScrapperApiRequestException {
        service.postTgChat(id);
    }

    public void deleteTelegramChat(long id) throws ScrapperApiRequestException {
        service.deleteTgChat(id);
    }

    public ListLinksResponse getLinks(long id) throws ScrapperApiRequestException {
        return service.getLinks(id);
    }

    public LinkResponse postLinks(long id, AddLinkRequest addLinkRequest) throws ScrapperApiRequestException {
        return service.postLink(id, addLinkRequest);
    }

    public LinkResponse deleteLinks(long id, RemoveLinkRequest removeLinkRequest) throws ScrapperApiRequestException {
        return service.deleteLink(id, removeLinkRequest);
    }
}
