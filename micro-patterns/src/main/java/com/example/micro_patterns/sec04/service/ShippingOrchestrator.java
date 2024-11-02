package com.example.micro_patterns.sec04.service;

import com.example.micro_patterns.sec04.client.ShippingClient;
import com.example.micro_patterns.sec04.dto.Status;
import com.example.micro_patterns.sec04.dto.orchestration.OrchestrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ShippingOrchestrator extends Orchestrator {
    private final ShippingClient client;
    @Override
    public Mono<OrchestrationRequest> create(OrchestrationRequest request) {
        return client.schedule(request.getShippingRequest())
                .doOnNext(request::setShippingResponse)
                .thenReturn(request)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequest> isSuccess() {
        return orchestrationRequest -> Objects.nonNull(orchestrationRequest.getShippingResponse()) && orchestrationRequest.getShippingResponse().getStatus().equals(Status.SUCCESS);
    }

    @Override
    public Consumer<OrchestrationRequest> cancel() {
        return request -> Mono.just(request)
                .filter(isSuccess())
                .map(OrchestrationRequest::getShippingRequest)
                .doOnNext(client::cancel)
                .subscribe();
    }
}
