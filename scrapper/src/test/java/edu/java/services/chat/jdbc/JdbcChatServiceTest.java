package edu.java.services.chat.jdbc;

import edu.java.dao.repository.chat_repository.JdbcTgChatRepository;
import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.services.chat.TgChatService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JdbcChatServiceTest {

    @Test
    void registerChat() {
        long chatId = 1L;
        TgChatRepository repository = mock(JdbcTgChatRepository.class);
        doNothing().when(repository).add(chatId);
        TgChatService service = new JdbcChatService(repository);
        service.register(chatId);
        verify(repository, Mockito.times(1)).add(chatId);
    }

    @Test
    void unregisterChat() {
        long chatId = 1L;
        TgChatRepository repository = mock(JdbcTgChatRepository.class);
        doReturn(1L).when(repository).remove(chatId);
        TgChatService service = new JdbcChatService(repository);
        service.unregister(chatId);
        verify(repository, Mockito.times(1)).remove(chatId);
    }
}
