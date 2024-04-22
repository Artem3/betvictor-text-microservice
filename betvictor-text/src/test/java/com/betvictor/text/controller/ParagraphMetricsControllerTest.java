package com.betvictor.text.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.betvictor.text.dto.MetricsResponseDTO;
import com.betvictor.text.service.ParagraphMetricsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ParagraphMetricsController.class)
class ParagraphMetricsControllerTest {

    @MockBean
    private ParagraphMetricsService paragraphMetricsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void generateText() throws Exception {
        var expectedResponse = new MetricsResponseDTO("OK", 5.5, "700ns", "550ns");
        when(paragraphMetricsService.getMetrics(anyInt(), anyString())).thenReturn(expectedResponse);

        mockMvc.perform(get("/betvictor/text?p=1&l=short"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avg_paragraph_processing_time").value("700ns"));

        verify(paragraphMetricsService, times(1)).getMetrics(anyInt(), anyString());
    }

    @Test
    void validationHappened() throws Exception {
        mockMvc
                .perform(get("/betvictor/text"))
                .andExpect(status().isBadRequest());
    }
}