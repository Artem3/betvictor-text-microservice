package com.betvictor.text.controller;

import com.betvictor.text.dto.MetricsResponseDTO;
import com.betvictor.text.kafka.KafkaProducerService;
import com.betvictor.text.mapper.MetricsResponseMapper;
import com.betvictor.text.service.ParagraphMetricsService;
import com.betvictor.text.util.TimeUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/betvictor")
public class ParagraphMetricsController {

    private final ParagraphMetricsService paragraphMetricsService;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/text")
    public MetricsResponseDTO generateText(
            @RequestParam("p") @Min(1) int paragraphsQty,
            @RequestParam("l") @Pattern(regexp = "short|medium|long|verylong") String paragraphLengthType) {
        long startTime = System.nanoTime();

        MetricsResponseDTO metricsDto = paragraphMetricsService.getMetrics(paragraphsQty, paragraphLengthType);
        metricsDto.setTotalProcessingTime(TimeUtil.formatDuration((System.nanoTime() - startTime)));
        kafkaProducerService.sendMessage(MetricsResponseMapper.mapToMessage(metricsDto));

        return metricsDto;
    }
}
