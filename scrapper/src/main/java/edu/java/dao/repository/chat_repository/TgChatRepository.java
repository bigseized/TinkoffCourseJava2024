package edu.java.dao.repository.chat_repository;

import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface TgChatRepository {
    @Transactional(propagation = Propagation.REQUIRED)
    void save(Long chatId);

    Long remove(Long chatId);

    List<Long> findAll();
}
