package com.bd.example.domain.services;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;

public class UpdateProductService {
    private final ProductRepository repository;

    public UpdateProductService(final ProductRepository repository) {
        this.repository = repository;
    }

    public Product execute(final Product product) {
        final var existing = repository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product %d not find".formatted(product.getId())));
        product.setCreatedAT(existing.getCreatedAT());
        product.setCurrentInventory(existing.getCurrentInventory());
        return repository.save(product);
    }

}
