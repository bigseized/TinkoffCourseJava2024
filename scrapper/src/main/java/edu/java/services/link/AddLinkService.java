package edu.java.services.link;

import edu.java.controllers.dto.request.AddLinkRequest;
import edu.java.controllers.dto.response.LinkResponse;
import java.net.URI;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class AddLinkService {
    @SneakyThrows
    public LinkResponse addLink(long tgChatId, AddLinkRequest addLinkRequest) {
        //stub
        return new LinkResponse(1, new URI("https://example.com"));
    }
}
