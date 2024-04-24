package com.betvictor.text.mapper;

import com.betvictor.text.dto.MetricsResponseDTO;
import com.betvictor.text.kafka.payload.MetricsResponseMsg;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MetricsResponseMapper {

    public MetricsResponseMsg mapToMessage(MetricsResponseDTO dto) {
        MetricsResponseMsg msg = new MetricsResponseMsg();
        msg.setFreqWord(dto.getFreqWord());
        msg.setAvgParagraphSize(dto.getAvgParagraphSize());
        msg.setAvgParagraphProcessingTime(dto.getAvgParagraphProcessingTime());
        msg.setTotalProcessingTime(dto.getTotalProcessingTime());

        return msg;
    }
}
