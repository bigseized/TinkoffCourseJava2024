package edu.java.dao.repository.chat_repository;

import edu.java.dao.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {
}
