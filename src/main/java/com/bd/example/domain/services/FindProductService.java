package com.bd.example.domain.services;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class FindProductService {
    private final ProductRepository repository;

    public FindProductService(final ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findBy(final String name) {
        if(isNull(name)) {
            return findAll();
        }
        return repository.findBy(name);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(final Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto %d not foumd".formatted(id)));
    }

}
