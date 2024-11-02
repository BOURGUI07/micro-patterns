package com.example.micro_patterns.sec05.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CarReservationResponse(
        UUID reservationId,
        String category,
        LocalDate pickup,
        LocalDate drop,
        String city,
        Integer price
) {

}
