package edu.java.services.chat;

import edu.java.exceptions.ChatAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AddTelegramChatService {
    public void addChat(long id) {
        //chat exist stub
        if (id == HttpStatus.CONFLICT.value()) {
            throw new ChatAlreadyRegisteredException("Чат с таким ID уже зарегистрирован");
        }
    }
}
