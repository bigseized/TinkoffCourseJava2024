package edu.java.clients.api.bot.dto;

import java.net.URI;
import java.util.List;
import lombok.Builder;

@Builder
public record LinkUpdateRequest(long id,
                                URI url,
                                String description,
                                List<Long> tgChatIds) {
}
