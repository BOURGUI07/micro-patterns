package com.example.micro_patterns.sec03.dto.order;

import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.user.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderResponse{
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Status orderStatus;
    private Address shippingAddress;
    private String expectedDeliveryDate;

}
