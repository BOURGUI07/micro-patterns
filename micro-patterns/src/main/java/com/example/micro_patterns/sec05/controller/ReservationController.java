package com.example.micro_patterns.sec05.controller;

import com.example.micro_patterns.sec05.dto.ReservationItemRequest;
import com.example.micro_patterns.sec05.dto.ReservationResponse;
import com.example.micro_patterns.sec05.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec05")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> items) {
        return reservationService.reserve(items);
    }
}
