package com.example.micro_patterns.sec03.service;

import com.example.micro_patterns.sec03.dto.orchestration.OrchestrationRequest;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Orchestrator {

    public abstract Mono<OrchestrationRequest> create(OrchestrationRequest orchestrationRequest);
    public abstract Predicate<OrchestrationRequest> isSuccess();
    public abstract Consumer<OrchestrationRequest> cancel();

}
