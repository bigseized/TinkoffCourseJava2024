package edu.java.dao.repository;

import edu.java.clients.api.github.GitHubClient;
import edu.java.clients.api.stack_overflow.StackOverflowClient;
import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.chat_repository.TgChatRepository;
import edu.java.dao.dto.ChatLinkAssociationDTO;
import edu.java.dao.dto.Link;
import edu.java.dao.repository.link_repository.LinkRepository;
import edu.java.scrapper.IntegrationEnvironment;
import edu.java.services.updater.LinkUpdaterSevice;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = "spring.config.location=classpath:jdbcTest.yml")
class JooqRepositoryTest extends IntegrationEnvironment {
    @MockBean
    private LinkUpdaterSevice linkUpdaterSevice;
    @MockBean
    private GitHubClient gitHubClient;
    @MockBean
    private StackOverflowClient stackOverflowClient;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private TgChatRepository chatRepository;
    @Autowired
    private ChatLinkRepository associationRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DSLContext dslContext;

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jooq");
    }

    private static final URI TEST_LINK = URI.create("test.com");
    private static final URI TEST_LINK_2 = URI.create("test2.com");

    @Test
    @Transactional
    @Rollback
    public void linkRepositoryAddMethod() {
        linkRepository.save(new Link(null, TEST_LINK, null));
        assertTrue(isLinkExists(TEST_LINK));
    }

    @Test
    @Transactional
    @Rollback
    public void linkRepositoryRemoveMethod() {
        addLink(TEST_LINK);
        assertTrue(isLinkExists(TEST_LINK));
        linkRepository.remove(new Link(null, TEST_LINK, null));
        assertFalse(isLinkExists(TEST_LINK));
    }

    @Test
    @Transactional
    @Rollback
    public void linkRepositoryFindAllMethod() {
        addLink(TEST_LINK);
        addLink(TEST_LINK_2);
        List<URI> linkList = linkRepository.findAll().stream().map(Link::resource).toList();
        assertThat(linkList).containsExactlyInAnyOrder(TEST_LINK, TEST_LINK_2);
    }

    @Test
    @Transactional
    @Rollback
    public void linkRepositoryUpdateTime() throws InterruptedException {
        Link link = addLink(TEST_LINK);
        linkRepository.updateTime(link);
        assertDoesNotThrow(link::updatedAt);
    }

    private Link selectLink(String resource) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM link WHERE resource=?",
            new DataClassRowMapper<>(Link.class),
            resource
        );
    }

    @Test
    @Transactional
    @Rollback
    public void linkRepositoryGetLinkByResource() {
        Link link = addLink(TEST_LINK);
        List<Link> linkList = linkRepository.getLinkByResource(TEST_LINK.toString());
        assertThat(linkList.getFirst().resource()).isEqualTo(TEST_LINK);
    }

    @Test
    @Transactional
    @Rollback
    public void linkRepositoryFindAllNotUpdated() {
        addLink(TEST_LINK);
        addLink(TEST_LINK_2);
        List<URI> linkList = linkRepository.findAll().stream().map(Link::resource).toList();
        assertThat(linkList).containsExactlyInAnyOrder(TEST_LINK, TEST_LINK_2);
    }

    @SneakyThrows
    private boolean isLinkExists(URI link) {
        return !jdbcTemplate.query(
            "SELECT * FROM link WHERE resource=?",
            new DataClassRowMapper<>(Link.class),
            link.toString()
        ).isEmpty();
    }

    @SneakyThrows
    private Link addLink(URI link) {
        return jdbcTemplate.queryForObject(
            "INSERT INTO link VALUES(default, ?, default) RETURNING *",
            new DataClassRowMapper<>(Link.class),
            link.toString()
        );
    }

    @Test
    @Transactional
    @Rollback
    public void chatRepositoryAddMethod() {
        chatRepository.save(1L);
        assertTrue(isChatExists(1L));
    }

    private boolean isChatExists(Long id) {
        return !jdbcTemplate.query(
            "SELECT * FROM chat WHERE id=?",
            (resultSet, i) -> resultSet.getLong(1),
            id
        ).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    public void chatRepositoryRemoveMethod() {
        addChat(1L);
        assertTrue(isChatExists(1L));
        chatRepository.remove(1L);
        assertFalse(isChatExists(1L));
    }

    @SneakyThrows
    private void addChat(Long id) {
        jdbcTemplate.update(
            "INSERT INTO chat VALUES(?)",
            id
        );
    }

    @Test
    @Transactional
    @Rollback
    public void chatRepositoryFindAllMethod() {
        addChat(1L);
        addChat(2L);
        List<Long> list = chatRepository.findAll();
        assertThat(list).containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    @Transactional
    @Rollback
    public void associationRepositoryAddMethod() {
        addChat(1L);
        addLink(TEST_LINK);
        Long link_id = findLinkId(TEST_LINK.toString());
        associationRepository.save(link_id, 1L);
        assertTrue(isAssociationExists(link_id, 1L));
    }

    private Long findLinkId(String link) {
        return jdbcTemplate.query(
            "SELECT id FROM link WHERE resource=?",
            (resultSet, i) -> resultSet.getLong(1),
            link
        ).stream().findAny().orElse(0L);
    }

    private boolean isAssociationExists(Long linkId, Long chatId) {
        return !jdbcTemplate.query(
            "SELECT * FROM chat_link_association WHERE link_id=? AND chat_id=?;",
            new DataClassRowMapper<>(ChatLinkAssociationDTO.class),
            linkId, chatId
        ).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    public void associationRepositoryFindAssociationById() {
        addChat(1L);
        addLink(TEST_LINK);
        Long link_id = findLinkId(TEST_LINK.toString());
        addAssociation(link_id, 1L);
        List<ChatLinkAssociationDTO> list = associationRepository.findAssociationByIds(link_id, 1L);
        assertEquals(list.getFirst(), new ChatLinkAssociationDTO(link_id, 1L));
    }

    @Test
    @Transactional
    @Rollback
    public void associationRepositoryFindLinksByChatId() {
        addChat(1L);
        Link link = addLink(TEST_LINK);
        Long link_id = findLinkId(TEST_LINK.toString());
        addAssociation(link_id, 1L);
        List<Link> list = associationRepository.findLinksByChatId(1L);
        assertEquals(list.getFirst(), link);
    }

    @Test
    @Transactional
    @Rollback
    public void associationRepositoryFindChatsByLinkId() {
        addChat(1L);
        Link link = addLink(TEST_LINK);
        addAssociation(link.id(), 1L);
        List<Long> list = associationRepository.findChatsByLinkId(link.id());
        assertEquals(list.getFirst(), 1L);
    }

    private void addAssociation(Long linkId, Long chatId) {
        jdbcTemplate.update("INSERT INTO chat_link_association VALUES (?,?)",
            linkId, chatId
        );
    }

    @Test
    @Transactional
    @Rollback
    public void associationRepositoryFindAllMethod() {
        addChat(1L);
        addLink(TEST_LINK);
        Long link_id = findLinkId(TEST_LINK.toString());
        addAssociation(link_id, 1L);
        addChat(2L);
        addLink(TEST_LINK_2);
        Long link_id_2 = findLinkId(TEST_LINK_2.toString());
        addAssociation(link_id_2, 2L);
        List<ChatLinkAssociationDTO> list = associationRepository.findAll();
        assertThat(list).containsExactlyInAnyOrder(
            new ChatLinkAssociationDTO(link_id, 1L),
            new ChatLinkAssociationDTO(link_id_2, 2L)
        );
    }
}

