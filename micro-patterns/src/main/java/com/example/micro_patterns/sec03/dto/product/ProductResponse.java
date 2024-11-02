package com.example.micro_patterns.sec03.dto.product;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer id,
        String category,
        String description,
        Integer price
) {
}
