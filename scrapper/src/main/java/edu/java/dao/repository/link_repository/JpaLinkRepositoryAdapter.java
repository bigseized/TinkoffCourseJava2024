package edu.java.dao.repository.link_repository;

import edu.java.dao.dto.Link;
import edu.java.dao.entity.LinkEntity;
import edu.java.exceptions.LinkNotFoundException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaLinkRepositoryAdapter implements LinkRepository {

    private final JpaLinkRepository repository;
    private final static String EMPTY_MESSAGE = "";

    @Override
    public Link add(Link link) {
        LinkEntity linkEntity = toEntity(link);
        return toDto(repository.save(linkEntity));
    }

    @Override
    public Link remove(Link link) {
        List<LinkEntity> linkEntities = repository.findByResource(link.resource().toString());
        if (linkEntities.isEmpty()) {
            throw new LinkNotFoundException(EMPTY_MESSAGE);
        }
        repository.delete(linkEntities.getFirst());
        return link;
    }

    @Override
    public List<Link> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<Link> findAllNotUpdated() {
        return repository.findAllNotUpdated().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public void updateTime(Link link) {
        repository.update(link.id());
    }

    @Override
    public List<Link> getLinkByResource(String resource) {
        return repository.findByResource(resource).stream().map(this::toDto).toList();
    }

    private LinkEntity toEntity(Link link) {
        return new LinkEntity().id(link.id()).resource(String.valueOf(link.resource())).updatedAt(link.updatedAt());
    }

    private Link toDto(LinkEntity linkEntity) {
        return new Link(linkEntity.id(), URI.create(linkEntity.resource()), linkEntity.updatedAt());
    }

}
