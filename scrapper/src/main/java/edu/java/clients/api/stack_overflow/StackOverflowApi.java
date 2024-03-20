package edu.java.clients.api.stack_overflow;

import edu.java.clients.api.stack_overflow.dto.StackOverflowResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface StackOverflowApi {
    @GetExchange("/questions/{id}/?site=stackoverflow")
    StackOverflowResponseDTO fetchQuestionsInfo(
        @PathVariable String id,
        @RequestParam String accessToken,
        @RequestParam String key
    );
}
