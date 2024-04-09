package edu.java.bot.services;

import edu.java.bot.controllers.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "kafka-config", name = "enable", havingValue = "true")
public class KafkaUpdatesListener {
    private final UpdateService updateService;

    @KafkaListener(topics = "${kafka-config.updates-topic-name}",
                   groupId = "scrapper-updates")
    public void greetingListener(@Payload LinkUpdateRequest linkUpdateRequest) {
        log.info("Received message with id=" + linkUpdateRequest.id());
        updateService.processUpdate(linkUpdateRequest);
    }
}

