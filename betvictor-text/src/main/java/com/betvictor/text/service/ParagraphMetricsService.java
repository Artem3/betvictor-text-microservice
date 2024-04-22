package com.betvictor.text.service;

import com.betvictor.text.dto.MetricsResponseDTO;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParagraphMetricsService {

    private final WebClient webClient;

    public MetricsResponseDTO getMetrics(int paragraphsQty, String paragraphLengthType) {

        String rawTextResponse = executeRequest(paragraphsQty, paragraphLengthType);
        log.debug(rawTextResponse);

        Pattern pattern = Pattern.compile("<p>(.*?)</p>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(rawTextResponse);

        AtomicInteger totalWords = new AtomicInteger(0);
        AtomicInteger totalParagraphs = new AtomicInteger(0);

        Map<String, Integer> frequencyMap = new HashMap<>();
        AtomicInteger maxFrequency = new AtomicInteger(0);
        AtomicReference<String> mostFrequentWord = new AtomicReference<>("");

        Stream.generate(() -> matcher.find() ? matcher.group(1) : null)
                .takeWhile(Objects::nonNull)
                .forEach(paragraph -> {
                    int wordCount = Arrays.stream(paragraph.split("\\s+"))
                            .filter(word -> !word.isEmpty())
                            .map(word -> word.replaceAll("[.,;:!?]+$", ""))
                            .map(String::toLowerCase)
                            .peek(word -> updateFrequencyMap(word, frequencyMap, mostFrequentWord, maxFrequency))
                            .mapToInt(word -> 1)
                            .sum();
                    totalWords.addAndGet(wordCount);
                    totalParagraphs.incrementAndGet();
                    log.debug("Total words per paragraph: {}", wordCount);
                });

        double averageWords = (double) totalWords.get() / totalParagraphs.get();

        log.debug("Total paragraphs: {}", totalParagraphs.get());
        log.debug("Total words in whole text: {}", totalWords.get());
        log.debug("Average number of words per paragraph: {}", String.format("%.1f", averageWords));
        log.debug("Most frequent word: '{}' - Occurrences: {}", mostFrequentWord.get(), maxFrequency.get());

        return new MetricsResponseDTO("BetVictor", 5.5, 100.0, 200.0);
    }

    private String executeRequest(int paragraphsQty, String paragraphLengthType) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/{paragraphsQty}/{paragraphLengthType}")
                        .build(paragraphsQty, paragraphLengthType))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .doOnNext(body ->
                                log.error("Error response with status {} and body {}", response.statusCode(), body))
                        .then(Mono.error(new RuntimeException("Lorem ipsum API response error."))))
                .bodyToMono(String.class)
                .block();
    }

    private void updateFrequencyMap(String word, Map<String, Integer> frequencyMap,
                                    AtomicReference<String> mostFrequentWord, AtomicInteger maxFrequency) {
        int currentFrequency = frequencyMap.getOrDefault(word, 0) + 1;
        frequencyMap.put(word, currentFrequency);

        if (currentFrequency > maxFrequency.get()) {
            maxFrequency.set(currentFrequency);
            mostFrequentWord.set(word);
        }
    }
}
