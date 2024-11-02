package com.example.micro_patterns.sec05.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ReservationResponse(
        UUID reservationId,
        List<ReservationItemResponse> reservationItemResponses,
        Integer totalPrice
) {
}
