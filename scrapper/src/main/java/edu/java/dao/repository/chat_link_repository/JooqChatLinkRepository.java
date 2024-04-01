package edu.java.dao.repository.chat_link_repository;

import edu.java.dao.repository.entity.ChatLinkAssociationEntity;
import edu.java.dao.repository.entity.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.dao.repository.jooq.Tables.LINK;
import static edu.java.dao.repository.jooq.tables.ChatLinkAssociation.CHAT_LINK_ASSOCIATION;

@RequiredArgsConstructor
public class JooqChatLinkRepository implements ChatLinkRepository {

    private final DSLContext dslContext;

    public void save(Long linkId, Long chatId) {
        dslContext.insertInto(CHAT_LINK_ASSOCIATION).values(linkId, chatId).execute();
    }

    public void remove(Long linkId, Long chatId) {
        dslContext
            .deleteFrom(CHAT_LINK_ASSOCIATION)
            .where(CHAT_LINK_ASSOCIATION.LINK_ID.eq(linkId).and(
                CHAT_LINK_ASSOCIATION.CHAT_ID.eq(chatId)))
            .execute();
    }

    public List<ChatLinkAssociationEntity> findAll() {
        return dslContext.selectFrom(CHAT_LINK_ASSOCIATION)
            .fetchInto(ChatLinkAssociationEntity.class);
    }

    public List<ChatLinkAssociationEntity> findAssociationByIds(Long linkId, Long chatId) {
        return dslContext.selectFrom(CHAT_LINK_ASSOCIATION)
            .where(CHAT_LINK_ASSOCIATION.LINK_ID.eq(linkId)
                .and(CHAT_LINK_ASSOCIATION.CHAT_ID.eq(chatId)))
            .fetchInto(ChatLinkAssociationEntity.class);
    }

    public List<Link> findLinksByChatId(Long chatId) {
        return dslContext.select(CHAT_LINK_ASSOCIATION.LINK_ID.as("id"), LINK.RESOURCE, LINK.UPDATED_AT)
            .from(CHAT_LINK_ASSOCIATION)
            .innerJoin(LINK).on(CHAT_LINK_ASSOCIATION.LINK_ID.eq(LINK.ID))
            .where(CHAT_LINK_ASSOCIATION.CHAT_ID.eq(chatId))
            .fetchInto(Link.class);
    }

    @Transactional
    public List<Long> findChatsByLinkId(Long linkId) {
        return dslContext.select(CHAT_LINK_ASSOCIATION.CHAT_ID)
            .from(CHAT_LINK_ASSOCIATION)
            .where(CHAT_LINK_ASSOCIATION.LINK_ID.eq(linkId))
            .fetchInto(Long.class);
    }
}
