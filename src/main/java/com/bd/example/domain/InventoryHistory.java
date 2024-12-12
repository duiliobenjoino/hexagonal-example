package com.bd.example.domain;

import java.time.ZonedDateTime;

public class InventoryHistory {
    private Long id;
    private final ZonedDateTime createdAt;
    private final Integer productId;
    private final String productName;
    private final Long quantity;
    private final Long currentInventory;

    public InventoryHistory(final Product product, final Long quantity) {
        this.createdAt = ZonedDateTime.now();
        this.productId = product.getId();
        this.productName = product.getName();
        this.quantity = quantity;
        this.currentInventory = product.getCurrentInventory();
    }

    public InventoryHistory(final Long id, final ZonedDateTime createdAt, final Integer productId, final String productName, final Long quantity, final Long currentInventory) {
        this.id = id;
        this.createdAt = createdAt;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.currentInventory = currentInventory;
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getCurrentInventory() {
        return currentInventory;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        final InventoryHistory that = (InventoryHistory) o;
        return createdAt.compareTo(that.createdAt) == 0 && productId.equals(that.productId)
                && productName.equals(that.productName) && quantity.equals(that.quantity)
                && currentInventory.equals(that.currentInventory);
    }
}
