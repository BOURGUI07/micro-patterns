package com.example.micro_patterns.sec05.mapper;

import com.example.micro_patterns.sec05.dto.*;

import java.util.List;
import java.util.UUID;

public class Mapper {

    public static CarReservationRequest toCarRequest(ReservationItemRequest req) {
        return CarReservationRequest.builder()
                .category(req.category())
                .city(req.city())
                .drop(req.to())
                .pickup(req.from())
                .build();
    }

    public static RoomReservationRequest toRoomRequest(ReservationItemRequest req) {
        return RoomReservationRequest.builder()
                .category(req.category())
                .city(req.city())
                .checkin(req.from())
                .checkout(req.to())
                .build();
    }

    public static ReservationItemResponse toCarResponse(CarReservationResponse res) {
        return ReservationItemResponse.builder()
                .category(res.category())
                .city(res.city())
                .from(res.pickup())
                .to(res.drop())
                .itemId(res.reservationId())
                .price(res.price())
                .build();
    }

    public static ReservationItemResponse toRoomResponse(RoomReservationResponse res) {
        return ReservationItemResponse.builder()
                .category(res.category())
                .city(res.city())
                .from(res.checkin())
                .to(res.checkout())
                .itemId(res.reservationId())
                .price(res.price())
                .build();
    }

    public static ReservationResponse toResponse(List<ReservationItemResponse> res) {
        return ReservationResponse.builder()
                .reservationItemResponses(res)
                .totalPrice(res.stream().mapToInt(ReservationItemResponse::price).sum())
                .reservationId(UUID.randomUUID())
                .build();
    }
}
