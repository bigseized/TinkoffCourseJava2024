package edu.java.dao.repository.chat_link_repository;

import edu.java.dao.repository.entity.ChatLinkAssociationEntity;
import edu.java.dao.repository.entity.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JdbcChatLinkRepository implements ChatLinkRepository {
    private final JdbcTemplate jdbcTemplate;

    public void add(Long linkId, Long chatId) {
        jdbcTemplate.update("INSERT INTO chat_link_association VALUES (?, ?)", linkId, chatId);
    }

    public void remove(Long linkId, Long chatId) {
        jdbcTemplate.update("DELETE FROM chat_link_association WHERE link_id=? AND chat_id=?", linkId, chatId);
    }

    public List<ChatLinkAssociationEntity> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM chat_link_association",
            new DataClassRowMapper<>(ChatLinkAssociationEntity.class)
        );
    }

    public List<ChatLinkAssociationEntity> findAssociationByIds(Long linkId, Long chatId) {
        return jdbcTemplate.query(
            "SELECT * FROM chat_link_association WHERE link_id = ? AND chat_id = ?",
            new DataClassRowMapper<>(ChatLinkAssociationEntity.class),
            linkId,
            chatId
        );
    }

    public List<Link> findLinksByChatId(Long chatId) {
        return jdbcTemplate.query(
            """
                SELECT link_id AS id, resource, updated_at
                FROM chat_link_association AS cla INNER JOIN link ON cla.link_id = link.id
                WHERE chat_id=?;
                """,
            new DataClassRowMapper<>(Link.class),
            chatId
        );
    }

    @Transactional
    public List<Long> findChatsByLinkId(Long linkId) {
        return jdbcTemplate.query(
            "SELECT chat_id FROM chat_link_association WHERE link_id=?;",
            (resultSet, i) -> resultSet.getLong("chat_id"),
            linkId
        );
    }
}
