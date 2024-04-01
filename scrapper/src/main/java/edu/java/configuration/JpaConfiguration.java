package edu.java.configuration;

import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.chat_link_repository.JpaChatLinkRepositoryAdapter;
import edu.java.dao.repository.chat_repository.JpaChatRepository;
import edu.java.dao.repository.chat_repository.JpaChatRepositoryAdapter;
import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.dao.repository.link_repository.JpaLinkRepository;
import edu.java.dao.repository.link_repository.JpaLinkRepositoryAdapter;
import edu.java.dao.repository.link_repository.LinkRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaConfiguration {
    @Bean
    public TgChatRepository jpaChatRepositoryAdapter(JpaChatRepository chatRepository) {
        return new JpaChatRepositoryAdapter(chatRepository);
    }

    @Bean
    public LinkRepository jpaLinkRepositoryAdapter(JpaLinkRepository linkRepository) {
        return new JpaLinkRepositoryAdapter(linkRepository);
    }

    @Bean
    public ChatLinkRepository jpaChatLinkRepositoryAdapter(
        JpaLinkRepository linkRepository,
        JpaChatRepository chatRepository
    ) {
        return new JpaChatLinkRepositoryAdapter(linkRepository, chatRepository);
    }

}
