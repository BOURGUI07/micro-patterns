package com.example.micro_patterns.sec03.service;

import com.example.micro_patterns.sec03.client.ProductClient;
import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.orchestration.OrchestrationRequest;
import com.example.micro_patterns.sec03.dto.order.OrderRequest;
import com.example.micro_patterns.sec03.dto.order.OrderResponse;
import com.example.micro_patterns.sec03.util.DebugUtil;
import com.example.micro_patterns.sec03.util.OrchestrationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderOrchestrationService {
    private final ProductClient productClient;
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> request) {
        return request
                .map(OrchestrationRequest::new)
                .flatMap(this::updatePrice)
                .doOnNext(OrchestrationUtil::buildOrchestrationRequest)
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::handlePostOrderProcessing)
                .doOnNext(DebugUtil::debugOrchestrator)
                .map(this::returnOrderResponse);
    }



    private OrderResponse returnOrderResponse(OrchestrationRequest orchestrationRequest) {
        var address = Status.SUCCESS.equals(orchestrationRequest.getStatus()) ? orchestrationRequest.getShippingResponse().getDeliveryAddress() : null;
        var date = Status.SUCCESS.equals(orchestrationRequest.getStatus()) ? orchestrationRequest.getShippingResponse().getExpectedDelivery() : null;
        return
                OrderResponse.builder()
                        .orderStatus(orchestrationRequest.getStatus())
                        .orderId(orchestrationRequest.getOrderId())
                        .userId(orchestrationRequest.getOrderRequest().userId())
                        .expectedDeliveryDate(date)
                        .productId(orchestrationRequest.getOrderRequest().productId())
                        .shippingAddress(address)
                        .build();
    }

    private void handlePostOrderProcessing(OrchestrationRequest orchestrationRequest) {
        if(Status.FAILED.equals(orchestrationRequest.getStatus())) {
            orderCancellationService.cancel(orchestrationRequest);
        }
    }

    private Mono<OrchestrationRequest> updatePrice(OrchestrationRequest orchestrationRequest) {
        return productClient.getProductInformation(orchestrationRequest.getOrderRequest().productId())
                .doOnNext(x->orchestrationRequest.setPrice(x.price()))
                .map(i->orchestrationRequest);
    }
}
