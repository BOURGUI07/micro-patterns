package com.example.micro_patterns.sec03.service;

import com.example.micro_patterns.sec03.dto.orchestration.OrchestrationRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCancellationService {
    private final List<Orchestrator> orchestrators;
    private Sinks.Many<OrchestrationRequest> sink;
    private Flux<OrchestrationRequest> flux;

    @PostConstruct
    public void init() {
        sink = Sinks.many().multicast().onBackpressureBuffer();
        flux = sink.asFlux().publishOn(Schedulers.boundedElastic());
        orchestrators.forEach(x->flux.subscribe(x.cancel()));
    }

    public void cancel(OrchestrationRequest orchestrationRequest) {
        sink.tryEmitNext(orchestrationRequest);
    }
}
