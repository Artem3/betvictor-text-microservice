package com.betvictor.text.kafka;

import com.betvictor.text.kafka.payload.MetricsResponseMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${producer.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, MetricsResponseMsg> kafkaTemplate;

    public void sendMessage(MetricsResponseMsg message) {
        var future = kafkaTemplate.send(topicName, message.getFreqWord(), message);
        future.thenAccept(result ->
                        log.debug("Message sent successfully to topic '{}', on partition {}, with offset {}",
                                result.getProducerRecord().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()))
                .exceptionally(ex -> {
                    log.error("Failed to send message", ex);
                    return null;
                });
    }
}
