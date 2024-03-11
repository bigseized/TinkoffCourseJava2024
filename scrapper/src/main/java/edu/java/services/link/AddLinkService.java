package edu.java.services.link;

import edu.java.controllers.dto.request.AddLinkRequest;
import edu.java.controllers.dto.response.LinkResponse;
import edu.java.dao.repository.LinkRepository;
import edu.java.dao.repository.model.Link;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddLinkService {
    private final LinkRepository linkRepository;

    @SuppressWarnings("checkstyle:MagicNumber")
    @Transactional
    public LinkResponse addLink(long tgChatId, AddLinkRequest addLinkRequest) {
        linkRepository.add(new Link(0L, addLinkRequest.link(), new Timestamp(1000000L)));
        return new LinkResponse(0L, addLinkRequest.link());
    }
}
