package com.bd.example.domain.services;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;
import com.bd.example.domain.services.transactionControl.CustomTransactional;

public class CreateProductService {
    private final ProductRepository repository;

    public CreateProductService(final ProductRepository repository) {
        this.repository = repository;
    }

    @CustomTransactional
    public Product execute(final Product product) {
        final var existing = repository.findBy(product.getName());
        if (!existing.isEmpty()) {
            throw new RuntimeException("Product '%s' already exists".formatted(product.getName()));
        }
        return repository.save(product);
    }

}
