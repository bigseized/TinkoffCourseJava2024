package edu.java.dao.repository.link_repository;

import edu.java.dao.dto.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    public Link add(Link link) {
        return jdbcTemplate.queryForObject(
            "INSERT INTO Link VALUES (default, ?, default) RETURNING *",
            new DataClassRowMapper<>(Link.class),
            link.resource().toString()
        );
    }

    public Link remove(Link link) {
        return jdbcTemplate.queryForObject(
            "DELETE FROM Link WHERE resource=? RETURNING *",
            new DataClassRowMapper<>(Link.class),
            link.resource().toString()
        );
    }

    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM Link", new DataClassRowMapper<>(Link.class));
    }

    @Transactional
    public List<Link> findAllNotUpdated() {
        String sql = """
            SELECT *
            FROM link
            WHERE link.updated_at + INTERVAL '1 minute' < timezone('UTC', CURRENT_TIMESTAMP);
            """;
        return jdbcTemplate.query(sql, new DataClassRowMapper<>(Link.class));
    }

    public void updateTime(Link link) {
        jdbcTemplate.update("UPDATE link SET updated_at=default WHERE id=?;", link.id());
    }

    public List<Link> getLinkByResource(String resource) {
        return jdbcTemplate.query(
            "SELECT * FROM link WHERE resource=?;",
            new DataClassRowMapper<>(Link.class),
            resource
        );
    }
}
