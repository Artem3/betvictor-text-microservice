package com.betvictor.text.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsResponseDTO {

    @JsonProperty("freq_word")
    private String freqWord;

    @JsonProperty("avg_paragraph_size")
    private double avgParagraphSize;

    @JsonProperty("avg_paragraph_processing_time")
    private String avgParagraphProcessingTime;

    @JsonProperty("total_processing_time")
    private String totalProcessingTime;
}
