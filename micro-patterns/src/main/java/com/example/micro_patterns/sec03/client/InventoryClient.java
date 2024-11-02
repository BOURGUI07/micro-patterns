package com.example.micro_patterns.sec03.client;

import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.inventory.InventoryRequest;
import com.example.micro_patterns.sec03.dto.inventory.InventoryResponse;
import com.example.micro_patterns.sec03.dto.user.PaymentRequest;
import com.example.micro_patterns.sec03.dto.user.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryClient {
    private WebClient webClient;

    public InventoryClient(@Value("${sec03.inventory.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return inventoryServicePostRequest("deduct", request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return inventoryServicePostRequest("restore", request);
    }

    private Mono<InventoryResponse> inventoryServicePostRequest(String action,InventoryRequest request) {
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
                .status(Status.FAILED)
                .remainingQuantity(null)
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
    }
}
