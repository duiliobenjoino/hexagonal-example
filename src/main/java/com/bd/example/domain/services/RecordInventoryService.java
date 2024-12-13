package com.bd.example.domain.services;

import com.bd.example.domain.InventoryHistory;
import com.bd.example.domain.ports.repositories.InventoryHistoryRepository;
import com.bd.example.domain.ports.repositories.ProductRepository;
import com.bd.example.domain.services.transactionControl.CustomTransactional;

public class RecordInventoryService {

    private final InventoryHistoryRepository inventoryHistoryRepository;
    private final ProductRepository productRepository;

    public RecordInventoryService(
            final InventoryHistoryRepository inventoryHistoryRepository,
            final ProductRepository productRepository) {
        this.inventoryHistoryRepository = inventoryHistoryRepository;
        this.productRepository = productRepository;
    }

    @CustomTransactional
    public InventoryHistory execute(final Integer productId, final Long value) {
        final var product = this.productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product '%s' not found".formatted(productId)));

        product.incrementInventory(value);
        productRepository.save(product);

        return inventoryHistoryRepository.save(new InventoryHistory(product, value));
    }

}
