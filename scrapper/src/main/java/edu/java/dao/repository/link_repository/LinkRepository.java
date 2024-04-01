package edu.java.dao.repository.link_repository;

import edu.java.dao.dto.Link;
import java.util.List;

public interface LinkRepository {

    Link save(Link link);

    Link remove(Link link);

    List<Link> findAll();

    List<Link> findAllNotUpdated();

    void updateTime(Link link);

    List<Link> getLinkByResource(String resource);
}
