package edu.java.dao.repository.link_repository;

import edu.java.dao.entity.LinkEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    @Query(value = """
            SELECT * FROM link l
            WHERE l.updated_at + interval '1 minute' < timezone('UTC', CURRENT_TIMESTAMP)
        """, nativeQuery = true)
    List<LinkEntity> findAllNotUpdated();

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE link SET updated_at=default WHERE id= :linkId", nativeQuery = true)
    void update(Long linkId);

    List<LinkEntity> findByResource(String resource);
}
