package com.example.micro_patterns.sec05.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReservationItemResponse(
        UUID itemId,
        ReservationType type,
        String category,
        String city,
        LocalDate from,
        LocalDate to,
        Integer price
) {
}
