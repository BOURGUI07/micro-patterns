package com.example.micro_patterns.sec03.service;

import com.example.micro_patterns.sec03.client.InventoryClient;
import com.example.micro_patterns.sec03.client.UserClient;
import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.orchestration.OrchestrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
                .thenReturn(request);
    }

    @Override
    public Predicate<OrchestrationRequest> isSuccess() {
        return orchestrationRequest -> orchestrationRequest.getInventoryResponse().getStatus().equals(Status.SUCCESS);
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
