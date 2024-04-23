package com.betvictor.text.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.betvictor.text.dto.MetricsResponseDTO;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ParagraphMetricsServiceTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private ParagraphMetricsService service;

    @Test
    public void testGetMetrics() {
        var expectedWebClientResponse = "<p>One, two, three GO!</p><p>Go ahead!</p>";
        var expectedResponseDTO = new MetricsResponseDTO("go", 3.0, null, null);

        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(expectedWebClientResponse));

        MetricsResponseDTO actualResult = service.getMetrics(2, "short");

        assertEquals(expectedResponseDTO.getFreqWord(), actualResult.getFreqWord());
        assertEquals(expectedResponseDTO.getAvgParagraphSize(), actualResult.getAvgParagraphSize());
        assertNotNull(actualResult.getAvgParagraphProcessingTime());
        assertNull(actualResult.getTotalProcessingTime());
    }
}