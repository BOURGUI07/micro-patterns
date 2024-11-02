package com.example.micro_patterns.sec03.dto.inventory;

import com.example.micro_patterns.sec03.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class InventoryResponse{
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private Status status;
}
