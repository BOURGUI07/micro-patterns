package com.example.micro_patterns.sec05.service;

import com.example.micro_patterns.sec05.client.CarClient;
import com.example.micro_patterns.sec05.client.RoomClient;
import com.example.micro_patterns.sec05.dto.ReservationItemRequest;
import com.example.micro_patterns.sec05.dto.ReservationItemResponse;
import com.example.micro_patterns.sec05.dto.ReservationType;
import com.example.micro_patterns.sec05.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RoomHandler extends ReservationHandler{
    private final RoomClient roomClient;

    @Override
    protected ReservationType getType() {
        return ReservationType.ROOM;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> items) {
        return items
                .map(Mapper::toRoomRequest)
                .as(roomClient::reserve)
                .map(Mapper::toRoomResponse);
    }
}
