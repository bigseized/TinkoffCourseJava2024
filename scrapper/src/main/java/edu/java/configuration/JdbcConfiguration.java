package edu.java.configuration;

import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.chat_link_repository.JdbcChatLinkRepository;
import edu.java.dao.repository.chat_repository.JdbcTgChatRepository;
import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.dao.repository.link_repository.JdbcLinkRepository;
import edu.java.dao.repository.link_repository.LinkRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcConfiguration {
    @Bean
    public TgChatRepository jdbcChatRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcTgChatRepository(jdbcTemplate);
    }

    @Bean
    public LinkRepository jdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public ChatLinkRepository jdbcChatLinkRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcChatLinkRepository(jdbcTemplate);
    }

}
