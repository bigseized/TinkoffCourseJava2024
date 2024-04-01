package edu.java.services.link;

import edu.java.dao.repository.chat_link_repository.ChatLinkRepository;
import edu.java.dao.repository.entity.Link;
import edu.java.dao.repository.link_repository.LinkRepository;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.exceptions.LinkAlreadyRegisteredException;
import edu.java.exceptions.LinkNotFoundException;
import edu.java.exceptions.UnsupportedLinkFormatException;
import edu.java.services.link_resolver.AbstractLinkResolver;
import edu.java.services.link_resolver.LinkType;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final ChatLinkRepository associationRepository;
    private final AbstractLinkResolver abstractLinkResolver;

    @Autowired
    public LinkServiceImpl(
        LinkRepository linkRepository,
        ChatLinkRepository associationRepository,
        List<AbstractLinkResolver> resolvers
    ) {
        this.linkRepository = linkRepository;
        this.associationRepository = associationRepository;
        this.abstractLinkResolver = AbstractLinkResolver.makeChain(resolvers);
    }

    @Override
    @Transactional
    public Link add(Long tgChatId, URI url) {
        checkLink(url.toString());
        Link link = findOrAddLink(tgChatId, url);
        addAssociation(tgChatId, link);
        return link;
    }

    private Link findOrAddLink(Long tgChatId, URI url) {
        List<Link> linkList = linkRepository.getLinkByResource(url.toString());

        Link link;
        if (linkList.isEmpty()) {
            link = linkRepository.save(new Link(null, url, null));
        } else {
            link = linkList.getFirst();
            if (!associationRepository.findAssociationByIds(link.id(), tgChatId).isEmpty()) {
                throw new LinkAlreadyRegisteredException("Ссылка уже связана с чатом");
            }
        }
        return link;
    }

    private void addAssociation(Long tgChatId, Link link) {
        try {
            associationRepository.save(link.id(), tgChatId);
        } catch (DataIntegrityViolationException e) {
            throw new ChatNotFoundException("Не зарегистрированный чат не может быть связан со ссылкой");
        }
    }

    @Override
    @Transactional
    public Link remove(Long tgChatId, URI url) {
        Link removedLink;
        try {
            removedLink = linkRepository.remove(new Link(null, url, null));
        } catch (EmptyResultDataAccessException e) {
            throw new LinkNotFoundException("Ссылка не закрепена за данным чатом");
        }
        return removedLink;
    }

    @Override
    public List<Link> listAll(Long tgChatId) {
        return associationRepository.findLinksByChatId(tgChatId);
    }

    private void checkLink(String link) {
        LinkType type = abstractLinkResolver.checkLink(link);
        if (type.equals(LinkType.UNRESOLVED)) {
            throw new UnsupportedLinkFormatException("Ссылка введена неверно или не поддерживается");
        }
    }
}
