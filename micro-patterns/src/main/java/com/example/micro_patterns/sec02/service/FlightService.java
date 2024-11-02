package com.example.micro_patterns.sec02.service;

import com.example.micro_patterns.sec02.client.DeltaClient;
import com.example.micro_patterns.sec02.client.FrontierClient;
import com.example.micro_patterns.sec02.client.JetBlueClient;
import com.example.micro_patterns.sec02.dto.FlightResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FrontierClient frontierClient;
    private final JetBlueClient jetBlueClient;
    private final DeltaClient deltaClient;

    public Flux<FlightResult> getFlights(String from, String to) {
        return Flux.merge(frontierClient.getFlights(from, to), jetBlueClient.getFlights(from, to),deltaClient.getFlights(from, to))
                .take(Duration.ofSeconds(3));
    }

}
