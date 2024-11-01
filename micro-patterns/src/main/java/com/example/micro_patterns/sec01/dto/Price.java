package com.example.micro_patterns.sec01.dto;

import lombok.Builder;

@Builder
public record Price(
        Integer listPrice,
        Double discount,
        Double discountedPrice,
        Double amountSaved
) {
}
