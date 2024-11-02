package com.example.micro_patterns.sec05.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CarReservationRequest(
        String category,
        LocalDate pickup,
        LocalDate drop,
        String city
) {
}
