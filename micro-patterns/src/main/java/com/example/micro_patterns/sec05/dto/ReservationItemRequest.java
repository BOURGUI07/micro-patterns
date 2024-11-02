package com.example.micro_patterns.sec05.dto;

import lombok.Builder;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ReservationItemRequest(
        ReservationType type,
        String category,
        String city,
        LocalDate from,
        LocalDate to
) {
}
