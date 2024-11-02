package com.example.micro_patterns.sec03.dto.user;

import com.example.micro_patterns.sec03.dto.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse{
    Integer userId;
    String name;
    Integer amount;
    Status status;
}
