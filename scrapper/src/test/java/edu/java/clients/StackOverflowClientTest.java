package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.clients.api.stack_overflow.dto.StackOverflowQuestionDTO;
import edu.java.clients.api.stack_overflow.StackOverflowClient;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackOverflowClientTest {

    private WireMockServer wireMockServer;
    private StackOverflowQuestionDTO resultDTO;
    private static final OffsetDateTime TEST_UPDATE_TIME = OffsetDateTime.parse("2024-01-23T14:58:43Z");
    private static final String TEST_ID = "11227809";
    private static final String TEST_LINK =
        "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array";
    public static final String TEST_QUESTION =
        "Why is processing a sorted array faster than processing an unsorted array?";
    private static final String TEST_JSON = """
        {
        "items": [
        {
            "tags": [
                    "java",
                    "c++",
                    "performance",
                    "cpu-architecture",
                    "branch-prediction"
                  ],
            "last_activity_date": 1706021923,
            "title": "Why is processing a sorted array faster than processing an unsorted array?"
        }]}
        """;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(777);
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        resultDTO = new StackOverflowQuestionDTO();
        resultDTO.setQuestionText(TEST_QUESTION);
        resultDTO.setUpdateTime(TEST_UPDATE_TIME);
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Получение информации о вопросе (StackOverflowAPI)")
    public void stackOverflowTestDataFetch() {
        stubFor(get(urlEqualTo("/questions/" + TEST_ID + "/?site=stackoverflow&accessToken=test&key=test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(TEST_JSON)));

        StackOverflowClient stackOverflowClient = new StackOverflowClient(wireMockServer.baseUrl());
        ReflectionTestUtils.setField(stackOverflowClient,"accessToken","test");
        ReflectionTestUtils.setField(stackOverflowClient,"key","test");
        StackOverflowQuestionDTO response = stackOverflowClient.fetchQuestionsInfo(TEST_ID);
        assertEquals(response, resultDTO);
    }

}
