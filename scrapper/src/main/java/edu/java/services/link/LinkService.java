package edu.java.services.link;

import edu.java.dao.repository.entity.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(Long tgChatId, URI url);

    Link remove(Long tgChatId, URI url);

    List<Link> listAll(Long tgChatId);
}
