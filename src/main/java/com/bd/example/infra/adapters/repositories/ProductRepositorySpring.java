package com.bd.example.infra.adapters.repositories;

import com.bd.example.infra.adapters.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositorySpring extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByNameIgnoreCase(String name);
}
