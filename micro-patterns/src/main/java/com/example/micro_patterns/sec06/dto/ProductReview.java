package com.example.micro_patterns.sec06.dto;

import lombok.Builder;

@Builder
public record ProductReview(
        Integer id,
        String user,
        Integer rating,
        String comment
) {
}
