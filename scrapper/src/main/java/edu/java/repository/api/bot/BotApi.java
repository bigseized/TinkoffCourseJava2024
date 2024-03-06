package edu.java.repository.api.bot;

import edu.java.repository.api.bot.dto.LinkUpdateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BotApi {
    @PostExchange("/updates")
    void updateBot(@RequestBody LinkUpdateRequest linkUpdateRequest);
}
