package edu.java.dao.repository.chat_repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class JdbcTgChatRepository implements TgChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public void add(Long chatId) {
        jdbcTemplate.update("INSERT INTO Chat VALUES (?)", chatId);
    }

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
