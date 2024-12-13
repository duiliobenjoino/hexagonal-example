package com.bd.example.domain.ports.repositories;

import com.bd.example.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    void remove(Integer id);

    List<Product> findAll();

    List<Product> findBy(String name);

    Optional<Product> findById(Integer id);
}
