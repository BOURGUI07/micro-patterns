package com.example.micro_patterns.sec04.dto.product;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer id,
        String category,
        String description,
        Integer price
) {
}
