package com.example.micro_patterns.sec06.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductAggregate(
        Integer id,
        String category,
        String description,
        List<ProductReview> reviews
) {
}
