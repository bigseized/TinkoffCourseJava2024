package edu.java.controllers;

import edu.java.services.chat.TgChatService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TelegramChatsController {
    private final TgChatService linkService;

    @PostMapping("/{id}")
    public void postTgChat(@Validated @Min(value = 0, message = "ID должен быть >= 0") @PathVariable long id) {
        linkService.register(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTgChat(@Validated @Min(value = 0, message = "ID должен быть >= 0") @PathVariable long id) {
        linkService.unregister(id);
    }
}
