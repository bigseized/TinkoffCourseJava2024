package edu.java.services.link;

import edu.java.controllers.dto.request.AddLinkRequest;
import edu.java.controllers.dto.response.LinkResponse;
import edu.java.exceptions.LinkAlreadyRegisteredException;
import java.net.URI;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class AddLinkService {
    @SneakyThrows
    public LinkResponse addLink(long tgChatId, AddLinkRequest addLinkRequest) {
        //already exist stub
        if (addLinkRequest.link().toString().equals("https://exist.com")) {
            throw new LinkAlreadyRegisteredException("Ссылка уже содержится в отслеживаемых ресурсах");
        }
        //stub
        return new LinkResponse(1, new URI("https://example.com"));
    }
}
