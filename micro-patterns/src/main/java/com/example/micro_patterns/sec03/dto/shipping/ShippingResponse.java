package com.example.micro_patterns.sec03.dto.shipping;

import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.user.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingResponse {
    UUID orderId;
    Integer quantity;
    Status status;
    String expectedDelivery;
    Address deliveryAddress;
}
