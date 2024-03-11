package edu.java.bot.controllers;

import edu.java.bot.controllers.dto.request.LinkUpdateRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/updates")
public class BotUpdateController {
    @PostMapping
    public void postUpdates(
        @Validated @RequestBody LinkUpdateRequest linkUpdateRequest
    ) {
    }
}
