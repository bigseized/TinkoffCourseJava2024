package edu.java.bot.clients.api.scrapper;

import edu.java.bot.clients.api.scrapper.dto.request.AddLinkRequest;
import edu.java.bot.clients.api.scrapper.dto.request.RemoveLinkRequest;
import edu.java.bot.clients.api.scrapper.dto.response.LinkResponse;
import edu.java.bot.clients.api.scrapper.dto.response.ListLinksResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperApi {
    @PostExchange("/tg-chat/{id}")
    void postTgChat(@PathVariable long id);

    @DeleteExchange("tg-chat/{id}")
    void deleteTgChat(@PathVariable long id);

    @GetExchange("links")
    ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") long tgChatId);

    @PostExchange("links")
    LinkResponse postLink(
        @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    );

    @DeleteExchange("links")
    LinkResponse deleteLink(
        @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody RemoveLinkRequest removeLinkRequest
    );
}


