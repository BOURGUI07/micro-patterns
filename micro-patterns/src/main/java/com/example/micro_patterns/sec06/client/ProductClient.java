package com.example.micro_patterns.sec06.client;

import com.example.micro_patterns.sec06.dto.ProductInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductClient {
    private WebClient webClient;

    public ProductClient(@Value("${sec06.product.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<ProductInformation> getProductInformation(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductInformation.class)
                .timeout(Duration.ofMillis(900))
                .onErrorResume(ex->Mono.empty());
    }
}
