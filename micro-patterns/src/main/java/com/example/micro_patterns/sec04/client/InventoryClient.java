package com.example.micro_patterns.sec04.client;

import com.example.micro_patterns.sec04.dto.Status;
import com.example.micro_patterns.sec04.dto.inventory.InventoryRequest;
import com.example.micro_patterns.sec04.dto.inventory.InventoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryClient {
    private WebClient webClient;

    public InventoryClient(@Value("${sec04.inventory.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return inventoryServicePostRequest("deduct", request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return inventoryServicePostRequest("restore", request);
    }

    private Mono<InventoryResponse> inventoryServicePostRequest(String action, InventoryRequest request) {
        return webClient
                .post()
                .uri(action)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(errorResponse(request));
    }

    private InventoryResponse errorResponse(InventoryRequest request) {
        return InventoryResponse.builder()
                .inventoryId(null)
                .status(Status.FAILED)
                .remainingQuantity(null)
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
    }
}
