package com.example.micro_patterns.sec04.service;

import com.example.micro_patterns.sec04.client.InventoryClient;
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
public class InventoryOrchestrator extends Orchestrator {
    private final InventoryClient client;
    @Override
    public Mono<OrchestrationRequest> create(OrchestrationRequest request) {
        return client.deduct(request.getInventoryRequest())
                .doOnNext(request::setInventoryResponse)
                .thenReturn(request)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequest> isSuccess() {
        return orchestrationRequest -> Objects.nonNull(orchestrationRequest.getInventoryResponse()) &&orchestrationRequest.getInventoryResponse().getStatus().equals(Status.SUCCESS);
    }

    @Override
    public Consumer<OrchestrationRequest> cancel() {
        return request -> Mono.just(request)
                .filter(isSuccess())
                .map(OrchestrationRequest::getInventoryRequest)
                .doOnNext(client::restore)
                .subscribe();
    }
}
