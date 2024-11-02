package com.example.micro_patterns.sec04.dto.inventory;

import lombok.Builder;

import java.util.UUID;

@Builder
public record InventoryRequest(
        UUID paymentId,
        Integer productId,
        Integer quantity
) {
}
