package com.example.micro_patterns.sec04.dto.user;

import com.example.micro_patterns.sec04.dto.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse{
    UUID paymentId;
    Integer userId;
    String name;
    Integer amount;
    Status status;
}
