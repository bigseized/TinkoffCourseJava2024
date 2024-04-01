package edu.java.clients.api.bot.dto;

import edu.java.services.updater.EventType;
import java.net.URI;
import java.util.List;
import lombok.Builder;

@Builder
public record LinkUpdateRequest(long id,
                                URI url,
                                String description,
                                EventType eventType,
                                List<Long> tgChatIds) {
}
