package edu.java.dao.repository.chat_repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import static edu.java.dao.jooq_generated.tables.Chat.CHAT;

@RequiredArgsConstructor
public class JooqChatRepository implements TgChatRepository {
    private final DSLContext dslContext;

    public void save(Long chatId) {
        dslContext.insertInto(CHAT).values(chatId).execute();
    }

    public Long remove(Long chatId) {
        return dslContext.deleteFrom(CHAT)
            .where(CHAT.ID.eq(chatId))
            .returning()
            .fetchOneInto(Long.class);
    }

    public List<Long> findAll() {
        return dslContext.selectFrom(CHAT).fetchInto(Long.class);
    }
}
