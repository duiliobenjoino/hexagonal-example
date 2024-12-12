package com.bd.example.domain.services;

import com.bd.example.domain.ports.repositories.ProductRepository;
import com.bd.example.domain.services.transactionControl.CustomTransactional;

public class RemoveProductService {
    private final ProductRepository repository;


    public RemoveProductService(final ProductRepository repository) {
        this.repository = repository;
    }

    @CustomTransactional
    public void execute(final Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product %d not find".formatted(id)));
        repository.remove(id);
    }

}
