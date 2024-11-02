package com.example.micro_patterns.sec04.util;

import com.example.micro_patterns.sec04.dto.inventory.InventoryRequest;
import com.example.micro_patterns.sec04.dto.orchestration.OrchestrationRequest;
import com.example.micro_patterns.sec04.dto.shipping.ShippingRequest;
import com.example.micro_patterns.sec04.dto.user.PaymentRequest;

public class OrchestrationUtil {

    public static void buildPaymentRequest(OrchestrationRequest request) {
        request.setPaymentRequest(
                PaymentRequest.builder()
                        .amount(request.getPrice()*request.getOrderRequest().quantity())
                        .orderId(request.getOrderId())
                        .userId(request.getOrderRequest().userId())
                        .build()
        );
    }

    public static void buildInventoryRequest(OrchestrationRequest request) {
        request.setInventoryRequest(
                InventoryRequest.builder()
                        .paymentId(request.getPaymentResponse().getPaymentId())
                        .productId(request.getOrderRequest().productId())
                        .quantity(request.getOrderRequest().quantity())
                        .build()
        );
    }

    public static void buildShippingRequest(OrchestrationRequest request) {
        request.setShippingRequest(
            ShippingRequest.builder()
                    .inventoryId(request.getInventoryResponse().getInventoryId())
                    .userId(request.getOrderRequest().userId())
                    .quantity(request.getOrderRequest().quantity())
                    .build()
        );
    }

}
