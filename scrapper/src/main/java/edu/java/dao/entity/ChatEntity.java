package edu.java.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
@Entity
@Table(name = "chat")
public class ChatEntity {
    @Id
    @Column(name = "id", nullable = false) private Long id;

    @ManyToMany
    @JoinTable(name = "chat_link_association",
               joinColumns = @JoinColumn(name = "chat_id"),
               inverseJoinColumns = @JoinColumn(name = "link_id"))
    private Set<LinkEntity> linkEntities = new LinkedHashSet<>();

    public void addLink(LinkEntity entity) {
        linkEntities.add(entity);
        entity.chatEntities().add(this);
    }
}
