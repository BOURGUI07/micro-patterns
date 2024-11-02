package com.example.micro_patterns.sec05.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RoomReservationResponse(
        UUID reservationId,
        String category,
        LocalDate checkin,
        LocalDate checkout,
        String city,
        Integer price
) {
}
