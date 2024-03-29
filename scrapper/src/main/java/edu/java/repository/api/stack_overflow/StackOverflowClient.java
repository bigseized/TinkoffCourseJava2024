package edu.java.repository.api.stack_overflow;

import edu.java.dto.StackOverflowQuestionDTO;
import edu.java.dto.StackOverflowResponseDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static edu.java.parsers.LinkParseUtil.parseStackOverflow;

public class StackOverflowClient {
    private final WebClient webClient;
    private final static String DEFAULT_STACKOVERFLOW_URL = "https://api.stackexchange.com/2.3";

    public StackOverflowClient() {
        this(DEFAULT_STACKOVERFLOW_URL);
    }

    public StackOverflowClient(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    public StackOverflowQuestionDTO fetchQuestionsInfo(String link) {
        return webClient.get()
            .uri(parseStackOverflow(link))
            .retrieve()
            .bodyToMono(StackOverflowResponseDTO.class)
            .flatMap(response -> Mono.justOrEmpty(response.getItems().getFirst()))
            .block();

    }
}
