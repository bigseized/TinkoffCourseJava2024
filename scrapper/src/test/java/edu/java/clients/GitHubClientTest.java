package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.clients.api.github.GitHubClient;
import edu.java.clients.api.github.dto.GitHubReposDTO;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubClientTest {

    private WireMockServer wireMockServer;

    private GitHubReposDTO resultDTO;

    private static final OffsetDateTime TEST_UPDATE_TIME = OffsetDateTime.parse("2023-08-01T19:20:49Z");
    private static final String TEST_USER_NAME = "bigseized";
    private static final String TEST_REPOS_NAME = "Poetress";
    private static final String TEST_LINK = "https://github.com/bigseized/Poetress";
    private static final String TEST_JSON = "{\"pushed_at\": \"2023-08-01T19:20:49Z\"," +
                                            "\"name\": \"Poetress\"}";

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        resultDTO = new GitHubReposDTO();
        resultDTO.setReposName(TEST_REPOS_NAME);
        resultDTO.setUpdateTime(TEST_UPDATE_TIME);
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Получение информации о репозитории (GitHubAPI)")
    public void gitHubTestDataFetch() {
        stubFor(get(urlEqualTo("/repos/" + TEST_USER_NAME + "/" + TEST_REPOS_NAME))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(TEST_JSON)));

        GitHubClient gitHubClient = new GitHubClient(wireMockServer.baseUrl());
        GitHubReposDTO response = gitHubClient.fetchReposInfo(TEST_USER_NAME, TEST_REPOS_NAME);
        assertEquals(resultDTO, response);
    }
}
