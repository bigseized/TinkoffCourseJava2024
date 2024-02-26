package edu.java.services.chatsLogik;

import edu.java.exceptions.ChatNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteTelegramChatService {
    public void validateChatId(long id) {
        //stub
        if (id == 666) {
            throw new ChatNotFoundException("Чат по указанному ID не найден");
        }
    }
}
