package com.example.micro_patterns.sec01.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProductPromotion(
        Double discount,
        String type,
        Integer id,
        LocalDate endDate
) {
}
