package com.example.micro_patterns.sec03.controller;

import com.example.micro_patterns.sec03.dto.order.OrderRequest;
import com.example.micro_patterns.sec03.dto.order.OrderResponse;
import com.example.micro_patterns.sec03.service.OrderOrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec03")
@RequiredArgsConstructor
public class OrderController {
    private final OrderOrchestrationService orderOrchestrationService;

    @PostMapping
    public Mono<OrderResponse> placeOrder(@RequestBody Mono<OrderRequest> orderRequest) {
        return orderOrchestrationService.placeOrder(orderRequest);
    }
}
