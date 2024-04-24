package com.betvictor.text.kafka.payload;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsResponseMsg implements Serializable {

    private String freqWord;

    private double avgParagraphSize;

    private String avgParagraphProcessingTime;

    private String totalProcessingTime;
}
