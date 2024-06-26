package edu.java.services.chat.jdbc;

import edu.java.dao.repository.chat_repository.JdbcTgChatRepository;
import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.services.chat.TgChatService;
import edu.java.services.chat.TgChatServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TgChatServiceImplTest {

    @Test
    void registerChat() {
        long chatId = 1L;
        TgChatRepository repository = mock(JdbcTgChatRepository.class);
        doNothing().when(repository).save(chatId);
        TgChatService service = new TgChatServiceImpl(repository);
        service.register(chatId);
        verify(repository, Mockito.times(1)).save(chatId);
    }

    @Test
    void unregisterChat() {
        long chatId = 1L;
        TgChatRepository repository = mock(JdbcTgChatRepository.class);
        doReturn(1L).when(repository).remove(chatId);
        TgChatService service = new TgChatServiceImpl(repository);
        service.unregister(chatId);
        verify(repository, Mockito.times(1)).remove(chatId);
    }

    @Test
    void registerChatException() {
        long chatId = 1L;
        TgChatRepository repository = mock(JdbcTgChatRepository.class);
        doThrow(DuplicateKeyException.class).when(repository).save(chatId);
        TgChatService service = new TgChatServiceImpl(repository);
        assertThatThrownBy(() -> service.register(chatId)).isInstanceOf(ChatAlreadyRegisteredException.class);
        verify(repository, Mockito.times(1)).save(chatId);

    }

    @Test
    void unregisterChatException() {
        long chatId = 1L;
        TgChatRepository repository = mock(JdbcTgChatRepository.class);
        doThrow(EmptyResultDataAccessException.class).when(repository).remove(chatId);
        TgChatService service = new TgChatServiceImpl(repository);
        assertThatThrownBy(() -> service.unregister(chatId)).isInstanceOf(ChatNotFoundException.class);

        verify(repository, Mockito.times(1)).remove(chatId);
    }
}
