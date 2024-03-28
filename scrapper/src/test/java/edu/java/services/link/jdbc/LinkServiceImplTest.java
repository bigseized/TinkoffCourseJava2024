package edu.java.services.link.jdbc;

import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.chat_link_repository.JdbcChatLinkRepository;
import edu.java.dao.repository.entity.Link;
import edu.java.dao.repository.link_repository.JdbcLinkRepository;
import edu.java.dao.repository.link_repository.LinkRepository;
import edu.java.services.link.LinkService;
import edu.java.services.link.LinkServiceImpl;
import edu.java.services.link_resolver.AbstractLinkResolver;
import edu.java.services.link_resolver.GitHubResolver;
import edu.java.services.link_resolver.LinkType;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static java.net.URI.create;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LinkServiceImplTest {

    private static final URI URL = create("www.google.com");
    private static final Link LINK = new Link(null, URL, null);

    @Test
    void addLinkTest() {
        long chatId = 1L;
        LinkRepository repository = mock(JdbcLinkRepository.class);
        ChatLinkRepository chatLinkRepository = mock(JdbcChatLinkRepository.class);
        GitHubResolver gitHubResolver = mock(GitHubResolver.class);
        doReturn(LinkType.GITHUB_REPOS).when(gitHubResolver).checkLink(any());
        List<AbstractLinkResolver> abstractResolvers = List.of(gitHubResolver);
        doReturn(LINK).when(repository).save(LINK);
        LinkService service = new LinkServiceImpl(repository, chatLinkRepository, abstractResolvers);
        service.add(chatId, URL);
        verify(repository, Mockito.times(1)).save(LINK);
    }

    @Test
    void removeLinkTest() {
        long chatId = 1L;
        LinkRepository repository = mock(JdbcLinkRepository.class);
        ChatLinkRepository chatLinkRepository = mock(JdbcChatLinkRepository.class);
        GitHubResolver gitHubResolver = mock(GitHubResolver.class);
        List<AbstractLinkResolver> abstractResolvers = List.of(gitHubResolver);
        doReturn(LINK).when(repository).remove(LINK);
        LinkService service = new LinkServiceImpl(repository, chatLinkRepository, abstractResolvers);

        service.remove(chatId, URL);
        verify(repository, Mockito.times(1)).remove(LINK);
    }

    @Test
    void listAllLinksTest() {
        long chatId = 1L;
        LinkRepository repository = mock(JdbcLinkRepository.class);
        ChatLinkRepository chatLinkRepository = mock(JdbcChatLinkRepository.class);
        GitHubResolver gitHubResolver = mock(GitHubResolver.class);
        List<AbstractLinkResolver> abstractResolvers = List.of(gitHubResolver);
        doReturn(List.of(LINK)).when(chatLinkRepository).findLinksByChatId(chatId);
        LinkService service = new LinkServiceImpl(repository, chatLinkRepository, abstractResolvers);

        service.listAll(chatId);
        verify(chatLinkRepository, Mockito.times(1)).findLinksByChatId(chatId);
    }

}
