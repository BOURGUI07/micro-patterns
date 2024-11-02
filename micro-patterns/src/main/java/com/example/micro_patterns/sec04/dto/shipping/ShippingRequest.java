package com.example.micro_patterns.sec04.dto.shipping;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ShippingRequest(
        Integer userId,
        UUID inventoryId,
        Integer quantity
) {
}
