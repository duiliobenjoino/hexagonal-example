package com.bd.example.aplication.adapters.dtos;

import com.bd.example.aplication.adapters.validators.NotNullAndNotZero;
import lombok.Data;

@Data
public class InventoryRequestDTO {
    
    @NotNullAndNotZero(message = "Quantity is required")
    private Long quantity;
}
