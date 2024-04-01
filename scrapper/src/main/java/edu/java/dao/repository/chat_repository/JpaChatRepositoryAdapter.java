package edu.java.dao.repository.chat_repository;

import edu.java.dao.entity.ChatEntity;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatRepositoryAdapter implements TgChatRepository {

    private final JpaChatRepository repository;
    private final static String EMPTY_MESSAGE = "";

    @Override
    public void save(Long chatId) {
        if (repository.existsById(chatId)) {
            throw new ChatAlreadyRegisteredException(EMPTY_MESSAGE);
        }
        repository.save(new ChatEntity().id(chatId));
    }

    @Override
    public Long remove(Long chatId) {
        if (!repository.existsById(chatId)) {
            throw new ChatNotFoundException(EMPTY_MESSAGE);
        }
        repository.deleteById(chatId);
        return chatId;
    }

    @Override
    public List<Long> findAll() {
        return repository
            .findAll()
            .stream()
            .map(ChatEntity::id)
            .toList();
    }
}
