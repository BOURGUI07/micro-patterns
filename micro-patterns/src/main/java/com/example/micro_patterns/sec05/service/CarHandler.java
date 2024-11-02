package com.example.micro_patterns.sec05.service;

import com.example.micro_patterns.sec05.client.CarClient;
import com.example.micro_patterns.sec05.dto.ReservationItemRequest;
import com.example.micro_patterns.sec05.dto.ReservationItemResponse;
import com.example.micro_patterns.sec05.dto.ReservationType;
import com.example.micro_patterns.sec05.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.logging.Handler;

@Service
@RequiredArgsConstructor
public class CarHandler extends ReservationHandler {
    private final CarClient carClient;

    @Override
    protected ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> items) {
        return items
                .map(Mapper::toCarRequest)
                .as(carClient::reserve)
                .map(Mapper::toCarResponse);
    }
}
