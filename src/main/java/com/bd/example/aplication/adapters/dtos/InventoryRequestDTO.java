package com.bd.example.aplication.adapters.dtos;

import com.bd.example.aplication.adapters.validators.NotNullAndNotZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InventoryRequestDTO {

    @NotNullAndNotZero(message = "Quantity is required")
    private Long quantity;
}
