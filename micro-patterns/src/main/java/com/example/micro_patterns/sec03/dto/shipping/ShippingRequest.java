package com.example.micro_patterns.sec03.dto.shipping;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ShippingRequest(
        Integer userId,
        UUID orderId,
        Integer quantity
) {
}
