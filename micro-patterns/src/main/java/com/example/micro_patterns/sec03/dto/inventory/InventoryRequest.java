package com.example.micro_patterns.sec03.dto.inventory;

import lombok.Builder;

import java.util.UUID;

@Builder
public record InventoryRequest(
        UUID orderId,
        Integer productId,
        Integer quantity
) {
}
