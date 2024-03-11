package edu.java.dao.repository;

import edu.java.dao.repository.model.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(Link link) {
        jdbcTemplate.update("INSERT INTO Link VALUES (default, ?, ?)", link.url().toString(), link.timestamp());
    }

    @Transactional
    public void remove(Link link) {
        jdbcTemplate.update("DELETE FROM Link WHERE resource=?", link.url());
    }

    @Transactional
    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM Link", new BeanPropertyRowMapper<>(Link.class));
    }
}
