package edu.java.bot.repository.api.scrapper.dto.response;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, int size) {
}
