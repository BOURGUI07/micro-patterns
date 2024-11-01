package com.example.micro_patterns.sec01.client;

import com.example.micro_patterns.sec01.dto.ProductInformation;
import com.example.micro_patterns.sec01.dto.ProductPromotion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PromotionClient {
    private final ProductPromotion defaultPromotion=
            ProductPromotion.builder()
                    .id(-1)
                    .type("no promotion")
                    .discount(0.0)
                    .endDate(LocalDate.now())
                    .build();
    private WebClient webClient;

    public PromotionClient(@Value("${sec01.promotion.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<ProductPromotion> getProductPromotion(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductPromotion.class)
                .onErrorReturn(defaultPromotion);
    }
}
