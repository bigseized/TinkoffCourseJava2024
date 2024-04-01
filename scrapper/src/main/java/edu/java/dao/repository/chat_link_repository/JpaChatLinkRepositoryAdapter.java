package edu.java.dao.repository.chat_link_repository;

import edu.java.dao.dto.ChatLinkAssociationDTO;
import edu.java.dao.dto.Link;
import edu.java.dao.entity.ChatEntity;
import edu.java.dao.entity.LinkEntity;
import edu.java.dao.repository.chat_repository.JpaChatRepository;
import edu.java.dao.repository.link_repository.JpaLinkRepository;
import edu.java.exceptions.ChatNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaChatLinkRepositoryAdapter implements ChatLinkRepository {

    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;
    private final static String EMPTY_MESSAGE = "";

    @Override
    public void add(Long linkId, Long chatId) {
        ChatEntity chat = chatRepository
            .findById(chatId)
            .orElseThrow(() -> new ChatNotFoundException(EMPTY_MESSAGE));
        LinkEntity linkEntity = linkRepository.findById(linkId).orElse(null);
        chat.addLink(linkEntity);
    }

    @Override
    public List<ChatLinkAssociationDTO> findAll() {
        List<LinkEntity> linkEntities = linkRepository.findAll();
        List<ChatLinkAssociationDTO> associationsList = new ArrayList<>();
        for (var linkEntity : linkEntities) {
            associationsList.addAll(
                linkEntity
                    .chatEntities()
                    .stream()
                    .map(chatEntity -> new ChatLinkAssociationDTO(linkEntity.id(), chatEntity.id()))
                    .toList()
            );
        }
        return associationsList;
    }

    @Override
    public List<ChatLinkAssociationDTO> findAssociationByIds(Long linkId, Long chatId) {
        LinkEntity linkEntity = linkRepository.findById(linkId).orElse(new LinkEntity());
        return linkEntity
            .chatEntities()
            .stream()
            .filter(chatEntity -> chatEntity.id().equals(chatId))
            .map(chatEntity -> new ChatLinkAssociationDTO(linkId, chatEntity.id()))
            .toList();
    }

    @Override
    @Transactional
    public List<Long> findChatsByLinkId(Long linkId) {
        LinkEntity linkEntity = linkRepository.findById(linkId).orElse(new LinkEntity());
        return linkEntity.chatEntities().stream().map(ChatEntity::id).toList();
    }

    @Override
    public List<Link> findLinksByChatId(Long chatId) {
        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(new ChatEntity());
        return chatEntity.linkEntities().stream().map(this::toDto).toList();
    }

    private Link toDto(LinkEntity linkEntity) {
        return new Link(linkEntity.id(), URI.create(linkEntity.resource()), linkEntity.updatedAt());
    }
}
