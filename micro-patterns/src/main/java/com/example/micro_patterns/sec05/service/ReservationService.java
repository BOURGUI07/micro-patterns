package com.example.micro_patterns.sec05.service;

import com.example.micro_patterns.sec05.dto.ReservationItemRequest;
import com.example.micro_patterns.sec05.dto.ReservationItemResponse;
import com.example.micro_patterns.sec05.dto.ReservationResponse;
import com.example.micro_patterns.sec05.dto.ReservationType;
import com.example.micro_patterns.sec05.mapper.Mapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final Map<ReservationType,ReservationHandler> map;

    public ReservationService(List<ReservationHandler> handlers) {
        this.map = handlers.stream().collect(Collectors.toMap(
                ReservationHandler::getType,
                Function.identity()
        ));
    }

    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> items){
        return items
                .groupBy(ReservationItemRequest::type)
                .flatMap(this::aggregate)
                .collectList()
                .map(Mapper::toResponse);

    }

    private Flux<ReservationItemResponse> aggregate(GroupedFlux<ReservationType, ReservationItemRequest> groupedFlux) {
        var type = groupedFlux.key();
        var handler = map.get(type);
        return handler.reserve(groupedFlux);
    }

}
