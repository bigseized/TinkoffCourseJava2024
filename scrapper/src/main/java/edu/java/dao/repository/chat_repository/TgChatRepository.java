package edu.java.dao.repository.chat_repository;

import java.util.List;

public interface TgChatRepository {
    void add(Long chatId);

    Long remove(Long chatId);

    List<Long> findAll();
}
