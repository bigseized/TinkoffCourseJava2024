package edu.java.bot.controllers;

import edu.java.bot.controllers.dto.request.LinkUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/updates")
public class BotUpdateController {
    @PostMapping
    public void postUpdates(
        @Valid @RequestBody LinkUpdateRequest linkUpdateRequest
    ) {
    }
}
