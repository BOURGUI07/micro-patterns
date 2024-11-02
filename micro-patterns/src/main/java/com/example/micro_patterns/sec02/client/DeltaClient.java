package com.example.micro_patterns.sec02.client;

import com.example.micro_patterns.sec02.dto.FlightResult;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeltaClient {
    private WebClient webClient;

    public DeltaClient (@Value("${sec02.delta.service}") String baseUrl){
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Flux<FlightResult> getFlights(String from, String to){
        return webClient
                .get()
                .uri("{from}/{to}",from,to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(ex->Mono.empty());
    }
}
