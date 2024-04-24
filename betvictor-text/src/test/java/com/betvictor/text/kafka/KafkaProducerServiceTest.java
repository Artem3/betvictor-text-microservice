package com.betvictor.text.kafka;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.betvictor.text.kafka.payload.MetricsResponseMsg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"producer.topic.test.name"})
class KafkaProducerServiceTest {

    @Autowired
    private KafkaTemplate<String, MetricsResponseMsg> kafkaTemplate;

    @Test
    public void testSendMessage() throws Exception {
        MetricsResponseMsg message = new MetricsResponseMsg("word", 5.5, "1ms", "100ms");
        MetricsResponseMsg message2 = new MetricsResponseMsg("word2", 6.6, "2ms", "101ms");

        var future = kafkaTemplate.send("producer.topic.test.name", "key", message);
        var future2 = kafkaTemplate.send("producer.topic.test.name", "key", message2);

        var sendResult = future.get();
        var sendResult2 = future2.get();

        assertNotNull(sendResult.getRecordMetadata());
        assertTrue(sendResult.getRecordMetadata().offset() == 0, "Offset should be 0");
        assertTrue(sendResult2.getRecordMetadata().offset() > 0, "Offset should be 1");
    }
}