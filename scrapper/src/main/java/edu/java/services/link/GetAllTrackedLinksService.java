package edu.java.services.link;


import edu.java.controllers.dto.response.LinkResponse;
import edu.java.controllers.dto.response.ListLinksResponse;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class GetAllTrackedLinksService {

    @SneakyThrows
    public ListLinksResponse getAllTrackedLinks(long tgChatId) {
        //dummy data
        return new ListLinksResponse(List.of(new LinkResponse(0, new URI("https://example.com"))), 1);
    }
}
