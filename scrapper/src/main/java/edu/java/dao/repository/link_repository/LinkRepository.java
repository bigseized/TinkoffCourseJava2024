package edu.java.dao.repository.link_repository;

import edu.java.dao.repository.entity.Link;
import java.util.List;

public interface LinkRepository {

    Link add(Link link);

    Link remove(Link link);

    List<Link> findAll();

    List<Link> findAllNotUpdated();

    void updateTime(Link link);

    List<Link> getLinkByResource(String resource);
}