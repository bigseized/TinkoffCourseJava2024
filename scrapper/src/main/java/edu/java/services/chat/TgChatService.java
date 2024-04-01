package edu.java.services.chat;

public interface TgChatService {
    void register(Long tgChatId);

    void unregister(Long tgChatId);
}
