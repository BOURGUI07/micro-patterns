package com.example.micro_patterns.sec03.service;

import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.orchestration.OrchestrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {
    private final List<Orchestrator> orchestrators;

    public Mono<OrchestrationRequest> placeOrder(OrchestrationRequest request) {
        var list = orchestrators.stream().map(x->x.create(request)).toList();

        return Mono.zip(list,a->a[0])
                .cast(OrchestrationRequest.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequest orchestrationRequest) {
        var allSuccess = orchestrators.stream().allMatch(x->x.isSuccess().test(orchestrationRequest));
        var status = allSuccess? Status.SUCCESS:Status.FAILED;
        orchestrationRequest.setStatus(status);
    }


}
