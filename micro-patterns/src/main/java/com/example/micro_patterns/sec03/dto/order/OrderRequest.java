package com.example.micro_patterns.sec03.dto.order;

import lombok.Builder;

@Builder
public record OrderRequest(
        Integer userId,
        Integer productId,
        Integer quantity
) {
}
