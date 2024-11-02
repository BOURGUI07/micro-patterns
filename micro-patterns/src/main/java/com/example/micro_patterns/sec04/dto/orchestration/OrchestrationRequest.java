package com.example.micro_patterns.sec04.dto.orchestration;

import com.example.micro_patterns.sec04.dto.Status;
import com.example.micro_patterns.sec04.dto.inventory.InventoryRequest;
import com.example.micro_patterns.sec04.dto.inventory.InventoryResponse;
import com.example.micro_patterns.sec04.dto.order.OrderRequest;
import com.example.micro_patterns.sec04.dto.shipping.ShippingRequest;
import com.example.micro_patterns.sec04.dto.shipping.ShippingResponse;
import com.example.micro_patterns.sec04.dto.user.PaymentRequest;
import com.example.micro_patterns.sec04.dto.user.PaymentResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrchestrationRequest {
    UUID orderId = UUID.randomUUID();
    OrderRequest orderRequest;
    Integer price;
    PaymentRequest paymentRequest;
    InventoryRequest inventoryRequest;
    ShippingRequest shippingRequest;
    PaymentResponse paymentResponse;
    InventoryResponse inventoryResponse;
    ShippingResponse shippingResponse;
    Status status;

    public OrchestrationRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }
}
