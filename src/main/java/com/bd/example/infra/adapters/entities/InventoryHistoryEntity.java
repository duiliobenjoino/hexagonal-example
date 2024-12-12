package com.bd.example.infra.adapters.entities;

import com.bd.example.domain.InventoryHistory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "inventory_history")
@Getter
@NoArgsConstructor
public class InventoryHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "current_inventory")
    private Long currentInventory;

    public InventoryHistoryEntity(final InventoryHistory domain) {
        this.id = domain.getId();
        this.productId = domain.getProductId();
        this.productName = domain.getProductName();
        this.createdAt = domain.getCreatedAt();
        this.quantity = domain.getQuantity();
        this.currentInventory = domain.getCurrentInventory();
    }

    public InventoryHistory toDomain() {
        return new InventoryHistory(id, createdAt, productId, productName, quantity, currentInventory);
    }
}
