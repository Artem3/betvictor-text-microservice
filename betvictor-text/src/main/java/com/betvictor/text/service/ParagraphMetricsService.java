package com.betvictor.text.service;

import com.betvictor.text.dto.MetricsResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ParagraphMetricsService {

    public MetricsResponseDTO getMetrics(int paragraphsQty, String paragraphLengthType) {
        return new MetricsResponseDTO("BetVictor", 5.5, 100.0, 200.0);
    }
}
