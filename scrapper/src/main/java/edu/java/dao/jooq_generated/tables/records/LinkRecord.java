/*
 * This file is generated by jOOQ.
 */

package edu.java.dao.jooq_generated.tables.records;

import edu.java.dao.jooq_generated.tables.pojos.Link;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class LinkRecord extends UpdatableRecordImpl<LinkRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.link.id</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.link.id</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.link.resource</code>.
     */
    public void setResource(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.link.resource</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public String getResource() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.link.updated_at</code>.
     */
    public void setUpdatedAt(@Nullable LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.link.updated_at</code>.
     */
    @Nullable
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkRecord
     */
    public LinkRecord() {
        super(edu.java.dao.jooq_generated.tables.Link.LINK);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    @ConstructorProperties({"id", "resource", "updatedAt"})
    public LinkRecord(@Nullable Long id, @NotNull String resource, @Nullable LocalDateTime updatedAt) {
        super(edu.java.dao.jooq_generated.tables.Link.LINK);

        setId(id);
        setResource(resource);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    public LinkRecord(Link value) {
        super(edu.java.dao.jooq_generated.tables.Link.LINK);

        if (value != null) {
            setId(value.getId());
            setResource(value.getResource());
            setUpdatedAt(value.getUpdatedAt());
            resetChangedOnNotNull();
        }
    }
}