package edu.java.bot.controllers;

import edu.java.bot.controllers.dto.request.LinkUpdateRequest;
import edu.java.bot.services.UpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/updates")
@RequiredArgsConstructor
public class BotUpdateController {
    private final UpdateService updateService;

    @PostMapping
    public void postUpdates(
        @Validated @RequestBody LinkUpdateRequest linkUpdateRequest
    ) {
        updateService.processUpdate(linkUpdateRequest);
    }
}
