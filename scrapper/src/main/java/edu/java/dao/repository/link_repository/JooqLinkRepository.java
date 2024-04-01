package edu.java.dao.repository.link_repository;

import edu.java.dao.repository.entity.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.SQLDataType;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.dao.repository.jooq.Tables.LINK;
import static org.jooq.impl.DSL.defaultValue;
import static org.jooq.impl.DSL.field;

@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext dslContext;

    public Link save(Link link) {
        return dslContext
            .insertInto(LINK)
            .values(defaultValue(), link.resource().toString(), defaultValue()).returning()
            .fetchOneInto(Link.class);
    }

    public Link remove(Link link) {
        return dslContext.deleteFrom(LINK)
            .where(LINK.RESOURCE.eq(link.resource().toString()))
            .returning()
            .fetchOneInto(Link.class);
    }

    public List<Link> findAll() {
        return dslContext.selectFrom(LINK).fetchInto(Link.class);
    }

    @Transactional
    public List<Link> findAllNotUpdated() {
        return dslContext.selectFrom(LINK)
            .where("link.updated_at + INTERVAL '1 minute' < timezone('UTC', CURRENT_TIMESTAMP)")
            .fetchInto(Link.class);
    }

    public void updateTime(Link link) {
        dslContext.update(LINK)
            .set(field("updated_at"), defaultValue(SQLDataType.TIMESTAMP))
            .where(LINK.ID.eq(link.id()))
            .execute();
    }

    public List<Link> getLinkByResource(String resource) {
        return dslContext.selectFrom(LINK).where(LINK.RESOURCE.eq(resource)).fetchInto(Link.class);
    }
}
