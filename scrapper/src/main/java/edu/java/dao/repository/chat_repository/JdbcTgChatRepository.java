package edu.java.dao.repository.chat_repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository implements TgChatRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Long chatId) {
        jdbcTemplate.update("INSERT INTO Chat VALUES (?)", chatId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long remove(Long chatId) {
        return jdbcTemplate.queryForObject(
            "DELETE FROM Chat WHERE id=? RETURNING *;",
            (result, i) -> result.getLong(1),
            chatId
        );
    }

    public List<Long> findAll() {
        return jdbcTemplate.query("SELECT * FROM Chat", (resultSet, i) -> resultSet.getLong("id"));
    }
}
