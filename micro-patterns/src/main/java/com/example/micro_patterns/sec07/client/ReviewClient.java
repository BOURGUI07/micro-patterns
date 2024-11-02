package com.example.micro_patterns.sec07.client;

import com.example.micro_patterns.sec07.dto.ProductReview;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {
    private WebClient webClient;

    public ReviewClient(@Value("${sec07.review.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<List<ProductReview>> getProductReview(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->Mono.empty())
                .bodyToFlux(ProductReview.class)
                .collectList()
                .retry(5)
                .timeout(Duration.ofMillis(300))
                .onErrorReturn(Collections.emptyList());
    }
}
