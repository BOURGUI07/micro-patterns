package com.example.micro_patterns.sec09.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec09")
public class CalculatorController {

    @GetMapping("/calculate/{input}")
    @RateLimiter(name = "calculator-service",fallbackMethod = "fallback")
    public Mono<ResponseEntity<Integer>> doubleInteger(@PathVariable Integer input) {
        return Mono.fromSupplier(() -> 2*input)
                .map(ResponseEntity::ok);
    }

    public Mono<ResponseEntity<String>> fallback(@PathVariable Integer input, Throwable throwable) {
        return Mono.just(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(throwable.getMessage()));
    }
}
