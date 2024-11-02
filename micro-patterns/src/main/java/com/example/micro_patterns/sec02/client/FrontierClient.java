package com.example.micro_patterns.sec02.client;

import com.example.micro_patterns.sec02.dto.FlightResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FrontierClient {
    private WebClient webClient;

    public FrontierClient (@Value("${sec02.frontier.service}") String baseUrl){
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Flux<FlightResult> getFlights(String from, String to){
        var request = FrontierRequest.builder()
                .from(from)
                .to(to)
                .build();
        return webClient
                .post()
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(ex-> Mono.empty());
    }

    @Builder
    @AllArgsConstructor
    @Data
    private static class FrontierRequest{
        private String from;
        private String to;
    }
}
