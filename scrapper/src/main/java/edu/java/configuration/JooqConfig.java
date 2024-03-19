package edu.java.configuration;

import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.chat_link_repository.JooqChatLinkRepository;
import edu.java.dao.repository.chat_repository.JooqChatRepository;
import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.dao.repository.link_repository.JooqLinkRepository;
import edu.java.dao.repository.link_repository.LinkRepository;
import org.jooq.DSLContext;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqConfig {
    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }

    @Bean
    public TgChatRepository jooqChatRepository(DSLContext context) {
        return new JooqChatRepository(context);
    }

    @Bean
    public LinkRepository jooqLinkRepository(DSLContext context) {
        return new JooqLinkRepository(context);
    }

    @Bean
    public ChatLinkRepository jooqChatLinkRepository(DSLContext context) {
        return new JooqChatLinkRepository(context);
    }
}
