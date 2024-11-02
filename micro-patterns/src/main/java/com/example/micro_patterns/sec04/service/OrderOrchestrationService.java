package com.example.micro_patterns.sec04.service;

import com.example.micro_patterns.sec04.client.ProductClient;
import com.example.micro_patterns.sec04.dto.Status;
import com.example.micro_patterns.sec04.dto.orchestration.OrchestrationRequest;
import com.example.micro_patterns.sec04.dto.order.OrderRequest;
import com.example.micro_patterns.sec04.dto.order.OrderResponse;
import com.example.micro_patterns.sec04.util.DebugUtil;
import com.example.micro_patterns.sec04.util.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderOrchestrationService {
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> request) {
        return request
                .map(OrchestrationRequest::new)
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::handlePostOrderProcessing)
                .doOnNext(DebugUtil::debugOrchestrator)
                .map(this::returnOrderResponse);
    }



    private OrderResponse returnOrderResponse(OrchestrationRequest orchestrationRequest) {
        var isSuccess = Status.SUCCESS.equals(orchestrationRequest.getStatus());
        var address =  isSuccess? orchestrationRequest.getShippingResponse().getDeliveryAddress() : null;
        var date = isSuccess ? orchestrationRequest.getShippingResponse().getExpectedDelivery() : null;
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

}
