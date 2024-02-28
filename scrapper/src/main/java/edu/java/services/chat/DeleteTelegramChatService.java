package edu.java.services.chat;

import edu.java.exceptions.ChatNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteTelegramChatService {
    public void deleteChat(long id) {
        //stub
        if (id == 666) {
            throw new ChatNotFoundException("Чат по указанному ID не найден");
        }
    }
}
