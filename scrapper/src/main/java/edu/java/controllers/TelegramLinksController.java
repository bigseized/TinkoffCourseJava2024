package edu.java.controllers;

import edu.java.controllers.dto.request.AddLinkRequest;
import edu.java.controllers.dto.request.RemoveLinkRequest;
import edu.java.controllers.dto.response.LinkResponse;
import edu.java.controllers.dto.response.ListLinksResponse;
import edu.java.dao.dto.Link;
import edu.java.services.link.LinkService;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class TelegramLinksController {

    private final LinkService linkService;

    @GetMapping
    public ListLinksResponse getLinks(
        @Validated @Min(value = 0, message = "ChatID должен быть >= 0") @RequestHeader("Tg-Chat-Id") long tgChatId
    ) {
        List<LinkResponse> list = linkService.listAll(tgChatId).stream()
            .map(link -> new LinkResponse(link.id(), link.resource())).toList();
        return new ListLinksResponse(list, list.size());
    }

    @PostMapping
    public LinkResponse postLink(
        @Validated @Min(value = 0, message = "ChatID должен быть >= 0") @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        Link link = linkService.add(tgChatId, addLinkRequest.link());
        return new LinkResponse(link.id(), link.resource());
    }

    @DeleteMapping
    public LinkResponse deleteLink(
        @Validated @Min(value = 0, message = "ChatID должен быть >= 0") @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) {
        Link link = linkService.remove(tgChatId, removeLinkRequest.link());
        return new LinkResponse(link.id(), link.resource());
    }
}
