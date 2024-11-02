package com.example.micro_patterns.sec04.service;

import com.example.micro_patterns.sec04.client.ProductClient;
import com.example.micro_patterns.sec04.dto.Status;
import com.example.micro_patterns.sec04.dto.orchestration.OrchestrationRequest;
import com.example.micro_patterns.sec04.util.OrchestrationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class OrderFulfillmentService {
    ProductClient client;
    InventoryOrchestrator inventoryOrchestrator;
    PaymentOrchestrator paymentOrchestrator;
    ShippingOrchestrator shippingOrchestrator;


    public Mono<OrchestrationRequest> placeOrder(OrchestrationRequest request) {
        return this.updatePrice(request)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(paymentOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildInventoryRequest)
                .flatMap(inventoryOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildShippingRequest)
                .flatMap(shippingOrchestrator::create)
                .doOnNext(c->c.setStatus(Status.SUCCESS))
                .doOnError(ex-> request.setStatus(Status.FAILED))
                .onErrorReturn(request);

    }

    private Mono<OrchestrationRequest> updatePrice(OrchestrationRequest orchestrationRequest) {
        return client.getProductInformation(orchestrationRequest.getOrderRequest().productId())
                .doOnNext(x->orchestrationRequest.setPrice(x.price()))
                .map(i->orchestrationRequest);
    }


}
