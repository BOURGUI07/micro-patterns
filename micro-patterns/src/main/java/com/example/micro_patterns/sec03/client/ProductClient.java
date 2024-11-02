package com.example.micro_patterns.sec03.client;

import com.example.micro_patterns.sec01.dto.ProductInformation;
import com.example.micro_patterns.sec03.dto.product.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {
    private WebClient webClient;

    public ProductClient(@Value("${sec03.product.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<ProductResponse> getProductInformation(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorResume(ex->Mono.empty());
    }
}
