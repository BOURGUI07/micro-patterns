package com.example.micro_patterns.sec09.client;

import com.example.micro_patterns.sec09.dto.ProductReview;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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

    public ReviewClient(@Value("${sec09.review.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }


    @RateLimiter(name = "review-service",fallbackMethod = "fallbackReview")
    public Mono<List<ProductReview>> getProductReview(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->Mono.empty())
                .bodyToFlux(ProductReview.class)
                .collectList();
    }

    public Mono<List<ProductReview>> fallbackReview(Integer productId, Throwable throwable){
        return Mono.just(Collections.emptyList());
    }
}
