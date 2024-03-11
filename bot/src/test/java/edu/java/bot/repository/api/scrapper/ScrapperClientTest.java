package edu.java.bot.repository.api.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.repository.api.scrapper.dto.response.LinkResponse;
import edu.java.bot.repository.api.scrapper.dto.response.ListLinksResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

class ScrapperClientTest {
    private WireMockServer wireMockServer;
    private static final String LIST_LINKS_RESPONSE = """
            {
              "links": [
                {
                  "id": 1,
                  "url": "https://example.com"
                }
              ],
              "size": 1
            }
        """;
    private static final String LINK_RESPONSE = """
        {
            "id": 1,
            "url": "https://example.com"
        }
        """;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Отправка запроса на добавление чата (Scrapper)")
    public void postChatScrapper() {
        stubFor(post(urlEqualTo("/tg-chat/1"))
            .willReturn(aResponse()
                .withStatus(200)));

        ScrapperClient scrapperClient = new ScrapperClient(wireMockServer.baseUrl());
        scrapperClient.postTelegramChat(1);
        WireMock.verify(1, WireMock.postRequestedFor(WireMock.urlEqualTo("/tg-chat/1")));
    }

    @Test
    @DisplayName("Отправка запроса на удаление чата (Scrapper)")
    public void deleteChatScrapper() {
        stubFor(delete(urlEqualTo("/tg-chat/1"))
            .willReturn(aResponse()
                .withStatus(200)));

        ScrapperClient scrapperClient = new ScrapperClient(wireMockServer.baseUrl());
        scrapperClient.deleteTelegramChat(1);
        WireMock.verify(1, WireMock.deleteRequestedFor(WireMock.urlEqualTo("/tg-chat/1")));
    }

    @Test
    @DisplayName("Отправка запроса на получение ссылок (Scrapper)")
    public void getLinksScrapper() {
        stubFor(get(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(LIST_LINKS_RESPONSE)));

        ScrapperClient scrapperClient = new ScrapperClient(wireMockServer.baseUrl());
        var linkUpdateRequest = scrapperClient.getLinks(1);
        assertThat(linkUpdateRequest).isEqualTo(getListLinksResponseObj(LIST_LINKS_RESPONSE));
    }

    @SneakyThrows
    private ListLinksResponse getListLinksResponseObj(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, ListLinksResponse.class);
    }

    @Test
    @DisplayName("Отправка запроса на добавление ссылки (Scrapper)")
    public void postLinkScrapper() {
        stubFor(post(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(LINK_RESPONSE)));

        ScrapperClient scrapperClient = new ScrapperClient(wireMockServer.baseUrl());
        var linkUpdateRequest = scrapperClient.postLinks(1, null);
        assertThat(linkUpdateRequest).isEqualTo(getLinkResponseObj(LINK_RESPONSE));
    }

    @Test
    @DisplayName("Отправка запроса на удаление ссылки (Scrapper)")
    public void deleteLinkScrapper() {
        stubFor(delete(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(LINK_RESPONSE)));

        ScrapperClient scrapperClient = new ScrapperClient(wireMockServer.baseUrl());
        var linkUpdateRequest = scrapperClient.deleteLinks(1, null);
        assertThat(linkUpdateRequest).isEqualTo(getLinkResponseObj(LINK_RESPONSE));
    }

    @SneakyThrows
    private LinkResponse getLinkResponseObj(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, LinkResponse.class);
    }
}
