package com.example.micro_patterns.sec05.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RoomReservationRequest(
        String category,
        LocalDate checkin,
        LocalDate checkout,
        String city
) {

}
