package com.example.micro_patterns.sec07.client;

import com.example.micro_patterns.sec07.dto.ProductInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductClient {
    private WebClient webClient;

    public ProductClient(@Value("${sec07.product.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<ProductInformation> getProductInformation(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductInformation.class)
                .onErrorResume(ex->Mono.empty());
    }
}
