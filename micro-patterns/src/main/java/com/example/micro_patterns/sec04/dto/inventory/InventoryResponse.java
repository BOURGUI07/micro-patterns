package com.example.micro_patterns.sec04.dto.inventory;

import com.example.micro_patterns.sec04.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class InventoryResponse{
    private UUID inventoryId;
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private Status status;
}
