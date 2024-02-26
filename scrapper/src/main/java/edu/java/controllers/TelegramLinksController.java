package edu.java.controllers;

import edu.java.controllers.dto.request.AddLinkRequest;
import edu.java.controllers.dto.request.RemoveLinkRequest;
import edu.java.controllers.dto.response.LinkResponse;
import edu.java.controllers.dto.response.ListLinksResponse;
import edu.java.services.linksLogik.AddLinkService;
import edu.java.services.linksLogik.DeleteLinkService;
import edu.java.services.linksLogik.GetAllTrackedLinksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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

    private final GetAllTrackedLinksService getAllTrackedLinksService;
    private final AddLinkService addLinkService;
    private final DeleteLinkService deleteLinkService;

    @GetMapping
    public ListLinksResponse getLinks(
        @Valid @Min(value = 0, message = "ChatID должен быть >= 0") @RequestHeader("Tg-Chat-Id") long tgChatId
    ) {
        return getAllTrackedLinksService.getAllTrackedLinks(tgChatId);
    }

    @PostMapping
    public LinkResponse postLink(
        @Valid @Min(value = 0, message = "ChatID должен быть >= 0") @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        return addLinkService.addLink(tgChatId, addLinkRequest);
    }

    @DeleteMapping
    public LinkResponse deleteLink(
        @Valid @Min(value = 0, message = "ChatID должен быть >= 0") @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) {
        return deleteLinkService.deleteLink(tgChatId, removeLinkRequest);
    }
}
