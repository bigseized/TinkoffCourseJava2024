package edu.java.dao.repository.chat_link_repository;

import edu.java.dao.repository.entity.ChatLinkAssociationEntity;
import edu.java.dao.repository.entity.Link;
import java.util.List;

public interface ChatLinkRepository {
    void add(Long linkId, Long chatId);

    void remove(Long linkId, Long chatId);

    List<ChatLinkAssociationEntity> findAll();

    List<ChatLinkAssociationEntity> findAssociationByIds(Long linkId, Long chatId);

    List<Long> findChatsByLinkId(Long linkId);

    List<Link> findLinksByChatId(Long chatId);
}
