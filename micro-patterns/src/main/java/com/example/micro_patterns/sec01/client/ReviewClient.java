package com.example.micro_patterns.sec01.client;

import com.example.micro_patterns.sec01.dto.ProductPromotion;
import com.example.micro_patterns.sec01.dto.ProductReview;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {
    private WebClient webClient;

    public ReviewClient(@Value("${sec01.review.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<List<ProductReview>> getProductReview(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToFlux(ProductReview.class)
                .collectList()
                .onErrorReturn(Collections.emptyList());
    }
}
