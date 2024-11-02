package com.example.micro_patterns.sec05.client;

import com.example.micro_patterns.sec05.dto.CarReservationRequest;
import com.example.micro_patterns.sec05.dto.CarReservationResponse;
import com.example.micro_patterns.sec05.dto.RoomReservationRequest;
import com.example.micro_patterns.sec05.dto.RoomReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomClient {
    @Value("${sec05.room.service}")
    private String baseUrl;
    private final WebClient webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> flux) {
        return webClient.post()
                .body(flux, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(ex-> Mono.empty());
    }
}
