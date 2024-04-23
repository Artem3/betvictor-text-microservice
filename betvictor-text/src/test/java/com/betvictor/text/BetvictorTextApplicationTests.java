package com.betvictor.text;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.betvictor.text.controller.ParagraphMetricsController;
import com.betvictor.text.kafka.KafkaProducerService;
import com.betvictor.text.service.ParagraphMetricsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BetvictorTextApplicationTests {

    @Autowired
    private ParagraphMetricsController controller;

    @Autowired
    private ParagraphMetricsService service;

    @Autowired
    private KafkaProducerService producerService;

    @Test
    void contextLoads() {
        assertNotNull(controller);
        assertNotNull(service);
        assertNotNull(producerService);
    }
}
