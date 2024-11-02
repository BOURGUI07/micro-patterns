package com.example.micro_patterns.sec03.client;

import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.inventory.InventoryRequest;
import com.example.micro_patterns.sec03.dto.inventory.InventoryResponse;
import com.example.micro_patterns.sec03.dto.shipping.ShippingRequest;
import com.example.micro_patterns.sec03.dto.shipping.ShippingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShippingClient {
    private WebClient webClient;

    public ShippingClient(@Value("${sec03.shipping.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return shippingServicePostRequest("schedule", request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return shippingServicePostRequest("cancel", request);
    }

    private Mono<ShippingResponse> shippingServicePostRequest(String action,ShippingRequest request) {
        return webClient
                .post()
                .uri(action)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(errorResponse(request));
    }

    private ShippingResponse errorResponse(ShippingRequest request) {
        return ShippingResponse.builder()
                .status(Status.FAILED)
                .orderId(request.orderId())
                .deliveryAddress(null)
                .expectedDelivery(null)
                .quantity(request.quantity())
                .build();
    }
}
