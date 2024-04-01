package edu.java.services.chat;

import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements TgChatService {
    private final TgChatRepository chatRepository;

    @Override
    @Transactional
    public void register(Long tgChatId) {
        try {
            chatRepository.save(tgChatId);
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
