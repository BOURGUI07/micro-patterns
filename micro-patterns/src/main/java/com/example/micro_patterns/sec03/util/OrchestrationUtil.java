package com.example.micro_patterns.sec03.util;

import com.example.micro_patterns.sec03.dto.inventory.InventoryRequest;
import com.example.micro_patterns.sec03.dto.orchestration.OrchestrationRequest;
import com.example.micro_patterns.sec03.dto.shipping.ShippingRequest;
import com.example.micro_patterns.sec03.dto.user.PaymentRequest;

public class OrchestrationUtil {

    public static void buildOrchestrationRequest(OrchestrationRequest request) {
        buildInventoryRequest(request);
        buildShippingRequest(request);
        buildPaymentRequest(request);
    }

    private static void buildPaymentRequest(OrchestrationRequest request) {
        request.setPaymentRequest(
                PaymentRequest.builder()
                        .amount(request.getPrice()*request.getOrderRequest().quantity())
                        .orderId(request.getOrderId())
                        .userId(request.getOrderRequest().userId())
                        .build()
        );
    }

    private static void buildInventoryRequest(OrchestrationRequest request) {
        request.setInventoryRequest(
                InventoryRequest.builder()
                        .orderId(request.getOrderId())
                        .productId(request.getOrderRequest().productId())
                        .quantity(request.getOrderRequest().quantity())
                        .build()
        );
    }

    private static void buildShippingRequest(OrchestrationRequest request) {
        request.setShippingRequest(
            ShippingRequest.builder()
                    .orderId(request.getOrderId())
                    .userId(request.getOrderRequest().userId())
                    .quantity(request.getOrderRequest().quantity())
                    .build()
        );
    }

}
