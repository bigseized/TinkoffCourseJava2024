package edu.java.services;

import edu.java.clients.api.bot.dto.LinkUpdateRequest;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class ScrapperQueueProducer implements UpdatesSender {
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    @Value("${kafka-config.updates-topic-name}")
    private String topicName;

    public void updateBot(LinkUpdateRequest updateRequest) {
        CompletableFuture<SendResult<String, LinkUpdateRequest>>
            future = kafkaTemplate.send(topicName, updateRequest);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + updateRequest.id()
                         + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.error("Unable to send message=[" + updateRequest.id()
                          + "] due to : " + ex.getMessage());
            }
        });
    }
}
