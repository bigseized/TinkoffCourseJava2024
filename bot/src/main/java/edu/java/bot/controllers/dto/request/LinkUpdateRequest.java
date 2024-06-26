package edu.java.bot.controllers.dto.request;

import edu.java.bot.services.EventType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public record LinkUpdateRequest(@Min(value = 0, message = "ID должен быть >= 0") long id,
                                @NotNull(message = "URL не должен быть пустым")
                                URI url,
                                String description,
                                EventType eventType,
                                List<Long> tgChatIds) {
}
