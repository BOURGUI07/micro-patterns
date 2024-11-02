package com.example.micro_patterns.sec04.dto.user;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentRequest(
        Integer userId,
        Integer amount,
        UUID orderId
) {
}
