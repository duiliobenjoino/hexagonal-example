package com.bd.example.aplication.adapters.dtos;

import com.bd.example.domain.InventoryHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InventoryResponseDTO {
    private Long id;
    private ZonedDateTime createdAt;
    private Integer productId;
    private String productName;
    private Long quantity;
    private Long currentInventory;

    public static InventoryResponseDTO fromDomain(final InventoryHistory domain) {
        return new InventoryResponseDTO(
                domain.getId(),
                domain.getCreatedAt(),
                domain.getProductId(),
                domain.getProductName(),
                domain.getQuantity(),
                domain.getCurrentInventory()
        );
    }
}
