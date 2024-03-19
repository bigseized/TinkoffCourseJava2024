package edu.java.services.updater.jdbc;

import edu.java.clients.api.bot.BotClient;
import edu.java.clients.api.github.GitHubClient;
import edu.java.clients.api.github.dto.GitHubEventsDTO;
import edu.java.clients.api.github.dto.GitHubReposDTO;
import edu.java.clients.api.stack_overflow.StackOverflowClient;
import edu.java.clients.api.stack_overflow.dto.StackOverflowQuestionDTO;
import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.entity.Link;
import edu.java.dao.repository.link_repository.LinkRepository;
import edu.java.services.link_resolver.AbstractLinkResolver;
import edu.java.services.link_resolver.GitHubResolver;
import edu.java.services.link_resolver.StackOverflowResolver;
import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LinkUpdaterServiceTest {
    @Mock
    private LinkRepository linkRepository;

    @Mock
    private ChatLinkRepository associationRepository;

    @Mock
    private GitHubClient gitHubApi;

    @Mock
    private StackOverflowClient stackOverflowApi;

    @Mock
    private BotClient botClient;

    @Mock
    private List<AbstractLinkResolver> resolvers;

    @InjectMocks
    private LinkUpdaterService jdbcLinkUpdaterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resolvers = new ArrayList<>(Arrays.asList(new GitHubResolver(), new StackOverflowResolver()));
        AbstractLinkResolver resolver = AbstractLinkResolver.makeChain(resolvers);
        ReflectionTestUtils.setField(
            jdbcLinkUpdaterService,
            "abstractLinkResolver",
            resolver
        );
    }

    @Test
    void testUpdate_GitHubReposLink() {
        Link link = new Link(1L, URI.create("https://github.com/username/repository"), new Timestamp(1));
        when(linkRepository.findAllNotUpdated()).thenReturn(Collections.singletonList(link));
        GitHubReposDTO gitHubReposDTO = new GitHubReposDTO();
        gitHubReposDTO.setUpdateTime(OffsetDateTime.now());
        gitHubReposDTO.setReposName("RepositoryName");

        GitHubEventsDTO gitHubEventsDTO = new GitHubEventsDTO();
        gitHubEventsDTO.setType("default");
        when(gitHubApi.fetchReposInfo(anyString(), anyString())).thenReturn(gitHubReposDTO);
        when(gitHubApi.fetchEventInfo(anyString(), anyString())).thenReturn(gitHubEventsDTO);
        jdbcLinkUpdaterService.update();

        verify(botClient, times(1)).updateBot(any());
        verify(linkRepository, times(1)).updateTime(link);
    }

    @Test
    void testUpdate_StackOverflowQuestionLink() {
        Link link =
            new Link(1L, URI.create("https://stackoverflow.com/questions/12345678/sample-question"), new Timestamp(1));
        when(linkRepository.findAllNotUpdated()).thenReturn(Collections.singletonList(link));
        when(linkRepository.findAllNotUpdated()).thenReturn(Collections.singletonList(link));

        StackOverflowQuestionDTO stackOverflowQuestionDTO = new StackOverflowQuestionDTO();
        stackOverflowQuestionDTO.setUpdateTime(OffsetDateTime.now());
        stackOverflowQuestionDTO.setQuestionText("QuestionText");
        when(stackOverflowApi.fetchQuestionsInfo(anyString())).thenReturn(stackOverflowQuestionDTO);

        jdbcLinkUpdaterService.update();

        verify(botClient, times(1)).updateBot(any());
        verify(linkRepository, times(1)).updateTime(link);
    }

    @Test
    void testUpdate_UnresolvedLink() {
        Link link = new Link(1L, URI.create("https://example.com"), new Timestamp(1));

        when(linkRepository.findAllNotUpdated()).thenReturn(Collections.singletonList(link));

        jdbcLinkUpdaterService.update();

        verify(botClient, never()).updateBot(any());
        verify(linkRepository, never()).updateTime(link);
    }

}
