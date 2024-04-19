package com.betvictor.text.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MetricsResponseDTO(
        @JsonProperty("freq_word")
        String freqWord,

        @JsonProperty("avg_paragraph_size")
        double avgParagraphSize,

        @JsonProperty("avg_paragraph_processing_time")
        double avgParagraphProcessingTime,

        @JsonProperty("total_processing_time")
        double totalProcessingTime
) {
}
