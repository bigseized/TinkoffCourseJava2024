package edu.java.services.chat.jdbc;

import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.services.chat.TgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements TgChatService {
    private final TgChatRepository chatRepository;

    @Override
    @Transactional
    public void register(Long tgChatId) {
        try {
            chatRepository.add(tgChatId);
        } catch (DuplicateKeyException e) {
            throw new ChatAlreadyRegisteredException("Повторная регистрация чата");
        }
    }

    @Override
    @Transactional
    public void unregister(Long tgChatId) {
        try {
            chatRepository.remove(tgChatId);
        } catch (EmptyResultDataAccessException e) {
            throw new ChatNotFoundException("Чат с данным ID не зарегистрирован");
        }
    }
}
