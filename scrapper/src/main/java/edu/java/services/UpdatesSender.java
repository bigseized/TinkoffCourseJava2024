package edu.java.services;

import edu.java.clients.api.bot.dto.LinkUpdateRequest;

public interface UpdatesSender {
    void updateBot(LinkUpdateRequest linkUpdateRequest);
}
