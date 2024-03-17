package edu.java.services.updater.jdbc;

import edu.java.clients.api.bot.BotClient;
import edu.java.clients.api.bot.dto.LinkUpdateRequest;
import edu.java.clients.api.github.GitHubClient;
import edu.java.clients.api.github.dto.GitHubReposDTO;
import edu.java.clients.api.stack_overflow.StackOverflowClient;
import edu.java.clients.api.stack_overflow.dto.StackOverflowQuestionDTO;
import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.entity.Link;
import edu.java.dao.repository.link_repository.LinkRepository;
import edu.java.exceptions.clients.GitHubApiRequestException;
import edu.java.exceptions.clients.StackOverflowApiRequestException;
import edu.java.services.link_resolver.AbstractLinkResolver;
import edu.java.services.link_resolver.LinkType;
import edu.java.services.updater.LinkUpdater;
import edu.java.utilities.LinkParseUtil;
import jakarta.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class JdbcLinkUpdaterService implements LinkUpdater {
    public static final String INVALID_LINK = "Отслеживание ссылки прекращено. Причина: невалидная ссылка.";
    private final LinkRepository linkRepository;
    private final ChatLinkRepository associationRepository;

    private final GitHubClient gitHubApi;
    private final StackOverflowClient stackOverflowApi;
    private final BotClient botClient;

    private final List<AbstractLinkResolver> resolvers;
    private AbstractLinkResolver abstractLinkResolver;

    @SuppressWarnings("checkstyle:MissingSwitchDefault")
    @Override
    public void update() {
        List<Link> links = linkRepository.findAllNotUpdated();
        log.info("Updates check initiated - links found: " + links.size());
        for (var link : links) {
            String linkUrl = link.resource().toString();
            LinkType linkType = abstractLinkResolver.checkLink(linkUrl);
            switch (linkType) {
                case GITHUB_REPOS -> processUpdateFromGitHubRepos(link);
                case STACKOVERFLOW_QUESTION -> processUpdateFromStackOverflowQuestion(link);
                case UNRESOLVED -> log.error("База данных содержит неверную ссылку");
            }
        }
    }

    private void processUpdateFromGitHubRepos(Link link) {
        String[] args = LinkParseUtil.parseGitHubRepos(link.resource().toString());
        GitHubReposDTO gitHubReposDTO;
        try {
            gitHubReposDTO = gitHubApi.fetchReposInfo(args[0], args[1]);
        } catch (GitHubApiRequestException e) {
            handleUpdateException(link, INVALID_LINK);
            return;
        }
        handleUpdate(link, gitHubReposDTO.getUpdateTime(), gitHubReposDTO.getReposName());
    }

    private void processUpdateFromStackOverflowQuestion(Link link) {
        StackOverflowQuestionDTO dto;
        try {
            dto = stackOverflowApi.fetchQuestionsInfo(LinkParseUtil.parseStackOverflowQuestion(link.resource()
                .toString()));
        } catch (StackOverflowApiRequestException e) {
            handleUpdateException(link, INVALID_LINK);
            return;
        }
        handleUpdate(link, dto.getUpdateTime(), dto.getQuestionText());
    }

    private void handleUpdateException(Link link, String message) {
        botClient.updateBot(buildRequest(link, message));
        linkRepository.remove(link);
    }

    private void handleUpdate(Link link, OffsetDateTime updateTime, String description) {
        var lastUpdate = OffsetDateTime.of(link.updatedAt().toLocalDateTime(), ZoneOffset.UTC);
        if (updateTime.isAfter(lastUpdate)) {
            LinkUpdateRequest updateRequest = buildRequest(link, description);
            botClient.updateBot(updateRequest);
        }
        linkRepository.updateTime(link);
    }

    private LinkUpdateRequest buildRequest(Link link, String description) {
        List<Long> chatIds = getAllChatsByLink(link.id());
        return LinkUpdateRequest.builder()
            .id(link.id())
            .url(link.resource())
            .description(description)
            .tgChatIds(chatIds)
            .build();
    }

    private List<Long> getAllChatsByLink(Long linkId) {
        return associationRepository.findChatsByLinkId(linkId);
    }

    @PostConstruct
    private void setupResolvers() {
        abstractLinkResolver = AbstractLinkResolver.makeChain(resolvers);
    }
}
