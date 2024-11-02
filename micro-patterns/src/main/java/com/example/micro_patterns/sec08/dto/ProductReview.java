package com.example.micro_patterns.sec08.dto;

import lombok.Builder;

@Builder
public record ProductReview(
        Integer id,
        String user,
        Integer rating,
        String comment
) {
}
