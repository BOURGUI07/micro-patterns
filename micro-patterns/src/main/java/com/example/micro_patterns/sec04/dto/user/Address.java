package com.example.micro_patterns.sec04.dto.user;

import lombok.Builder;

@Builder
public record Address(
        String city,
        String state,
        String street,
        String zipCode
) {
}
