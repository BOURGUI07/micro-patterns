package com.example.micro_patterns.sec05.service;

import com.example.micro_patterns.sec05.dto.ReservationItemRequest;
import com.example.micro_patterns.sec05.dto.ReservationItemResponse;
import com.example.micro_patterns.sec05.dto.ReservationType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


public abstract class ReservationHandler {
    protected abstract ReservationType getType();
    protected abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> items);
}
