package com.example.micro_patterns.sec03.dto.user;

import lombok.Builder;

@Builder
public record UserResponse(
        Integer userId,
        Integer balance,
        String name,
        Address address
) {
}
