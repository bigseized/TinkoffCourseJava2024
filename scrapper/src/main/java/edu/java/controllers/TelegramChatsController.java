package edu.java.controllers;

import edu.java.services.chat.DeleteTelegramChatService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TelegramChatsController {
    private final DeleteTelegramChatService deleteTelegramChatService;

    @PostMapping("/{id}")
    public void postTgChat(@Min(value = 0, message = "ID должен быть >= 0") @PathVariable long id) {
    }

    @DeleteMapping("/{id}")
    public void deleteTgChat(@Min(value = 0, message = "ID должен быть >= 0") @PathVariable long id) {
        deleteTelegramChatService.deleteChat(id);
    }



}
