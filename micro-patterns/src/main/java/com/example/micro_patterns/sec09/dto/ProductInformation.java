package com.example.micro_patterns.sec09.dto;

import lombok.Builder;

@Builder
public record ProductInformation(
        Integer productId,
        String description,
        String category,
        Integer price
) {
}
