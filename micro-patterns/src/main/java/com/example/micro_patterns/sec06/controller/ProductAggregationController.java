package com.example.micro_patterns.sec06.controller;

import com.example.micro_patterns.sec06.dto.ProductAggregate;
import com.example.micro_patterns.sec06.service.ProductAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sec06")
public class ProductAggregationController {
    private final ProductAggregationService service;

    @GetMapping("/{productId}")
    public Mono<ProductAggregate> getProductAggregate(@PathVariable Integer productId) {
        return service.getProductAggregate(productId);
    }
}
