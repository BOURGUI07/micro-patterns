package com.example.micro_patterns.sec04.service;

import com.example.micro_patterns.sec04.client.UserClient;
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
public class PaymentOrchestrator extends Orchestrator {
    private final UserClient client;
    @Override
    public Mono<OrchestrationRequest> create(OrchestrationRequest request) {
        return client.deduct(request.getPaymentRequest())
                .doOnNext(request::setPaymentResponse)
                .thenReturn(request)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequest> isSuccess() {
        return orchestrationRequest -> Objects.nonNull(orchestrationRequest.getPaymentResponse()) && orchestrationRequest.getPaymentResponse().getStatus().equals(Status.SUCCESS);
    }

    @Override
    public Consumer<OrchestrationRequest> cancel() {
        return request -> Mono.just(request)
                .filter(isSuccess())
                .map(OrchestrationRequest::getPaymentRequest)
                .doOnNext(client::refund)
                .subscribe();
    }
}
