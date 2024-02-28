package edu.java.services.link;

import edu.java.controllers.dto.request.RemoveLinkRequest;
import edu.java.controllers.dto.response.LinkResponse;
import java.net.URI;
import edu.java.exceptions.LinkNotFoundException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class DeleteLinkService {
    @SneakyThrows
    public LinkResponse deleteLink(long tgChatId, RemoveLinkRequest deleteLinkService) {
        checkLink(deleteLinkService.link());
        //stub
        return new LinkResponse(1, new URI("https://example.com"));
    }

    private void checkLink(URI link) {
        //stub
        if (link.toString().equals("https://wrong.com")) {
            throw new LinkNotFoundException("Ссылка не была обнаружена в отслеживаемых ресурсах");
        }
    }
}
