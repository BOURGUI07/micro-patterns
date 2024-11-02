package com.example.micro_patterns.sec04.service;

import com.example.micro_patterns.sec04.dto.orchestration.OrchestrationRequest;
import com.example.micro_patterns.sec04.exception.OrderFulfillmentFailure;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Orchestrator {

    public abstract Mono<OrchestrationRequest> create(OrchestrationRequest orchestrationRequest);
    public abstract Predicate<OrchestrationRequest> isSuccess();
    public abstract Consumer<OrchestrationRequest> cancel();

    protected BiConsumer<OrchestrationRequest, SynchronousSink<OrchestrationRequest>> statusHandler() {
        return (req,sink) ->{
            if(isSuccess().test(req)) {
                sink.next(req);
            }else{
                sink.error(new OrderFulfillmentFailure());
            }
        };
    }

}
