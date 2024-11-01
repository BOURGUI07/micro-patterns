package com.example.micro_patterns.sec01.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductAggregate(
        Integer id,
        String category,
        String description,
        Price price,
        List<ProductReview> reviews
) {
}
