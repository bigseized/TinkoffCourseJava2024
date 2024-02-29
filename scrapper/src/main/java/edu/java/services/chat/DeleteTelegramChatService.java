package edu.java.services.chat;

import edu.java.exceptions.ChatNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteTelegramChatService {
    public void deleteChat(long id) {
        //chat not found stub
        if (id == HttpStatus.NOT_FOUND.value()) {
            throw new ChatNotFoundException("Чат по указанному ID не найден");
        }
    }
}
