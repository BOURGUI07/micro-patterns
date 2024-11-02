package com.example.micro_patterns.sec05.client;

import com.example.micro_patterns.sec05.dto.CarReservationRequest;
import com.example.micro_patterns.sec05.dto.CarReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarClient {
    @Value("${sec05.car.service}")
    private String baseUrl;
    private final WebClient webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();

    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> carRequests) {
        return webClient.post()
                .body(carRequests, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .onErrorResume(ex-> Mono.empty());
    }
}
