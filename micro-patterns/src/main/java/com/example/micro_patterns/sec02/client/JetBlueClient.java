package com.example.micro_patterns.sec02.client;

import com.example.micro_patterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JetBlueClient {
    private WebClient webClient;

    public JetBlueClient (@Value("${sec02.jetblue.service}") String baseUrl){
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Flux<FlightResult> getFlights(String from, String to){
        return webClient
                .get()
                .uri("{from}/{to}",from,to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .doOnNext(x-> normalizeResponse(x,from,to))
                .onErrorResume(ex-> Mono.empty());
    }

    private void normalizeResponse(FlightResult flightResult, String from, String to){
        flightResult.setFrom(from);
        flightResult.setTo(to);
        flightResult.setAirline("JETBLUE");
    }
}
