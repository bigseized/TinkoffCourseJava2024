package edu.java.dao.repository.chat_link_repository;

import edu.java.dao.dto.ChatLinkAssociationDTO;
import edu.java.dao.dto.Link;
import java.util.List;

public interface ChatLinkRepository {
    void save(Long linkId, Long chatId);

    List<ChatLinkAssociationDTO> findAll();

    List<ChatLinkAssociationDTO> findAssociationByIds(Long linkId, Long chatId);

    List<Long> findChatsByLinkId(Long linkId);

    List<Link> findLinksByChatId(Long chatId);
}

